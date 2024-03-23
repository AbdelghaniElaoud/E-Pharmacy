package com.epharmacy.app.service;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.enums.PaymentStatus;
import com.epharmacy.app.exceptions.CartNotFoundException;
import com.epharmacy.app.exceptions.DeliveryManNotFoundException;
import com.epharmacy.app.exceptions.PharmacistNotFoundException;
import com.epharmacy.app.exceptions.PrescriptionNotFoundException;
import com.epharmacy.app.mappers.OrderMapper;
import com.epharmacy.app.model.*;
import com.epharmacy.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final PharmacistRepository pharmacistRepository;
    private final OrderItemRepository orderItemRepository;
    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, DeliveryManRepository deliveryManRepository, PharmacistRepository pharmacistRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.orderItemRepository = orderItemRepository;
    }


    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }

    public Order save(Order item){
        return orderRepository.save(item);
    }
    public void delete(Order item){
        orderRepository.delete(item);
    }
    public void delete(Iterable<Order> items){
        orderRepository.deleteAll(items);
    }
    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public OrderDTO placeOrder(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()){
            throw new CartNotFoundException(cartId);
        }

        for (CartItem cartItem : cartOptional.get().getEntries()){
            if(cartItem.getAddedProduct().isPrescription() && cartOptional.get().getPrescriptions().isEmpty()){
                throw new PrescriptionNotFoundException(cartId);
            }
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartOptional.get().getEntries()) {
            OrderItem orderItem = new OrderItem(cartItem.getQuantity(), cartItem.getDiscount(), cartItem.getBasePrice(), cartItem.getTotalPrice(), cartItem.getAddedProduct());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }


        Set<Prescription> orderPrescriptions = new HashSet<>(cartOptional.get().getPrescriptions());

        DeliveryMan deliveryMan = deliveryManRepository.findAll().stream().findAny().orElse(null);
        Pharmacist pharmacist = pharmacistRepository.findAll().stream().findAny().orElse(null);

        if (deliveryMan == null) {
            throw new DeliveryManNotFoundException();
        }

        if (pharmacist == null) {
            throw new PharmacistNotFoundException();
        }
        deliveryManRepository.save(deliveryMan);
        pharmacistRepository.save(pharmacist);

        Order order = Order.builder()
                .address(cartOptional.get().getCustomer().getAddress())
                .orderItems(orderItems)
                .orderStatus(OrderStatus.INIT)
                .totalPrice(cartOptional.get().getTotalPrice())
                .paymentStatus(PaymentStatus.WAITING)
                .customer(cartOptional.get().getCustomer())
                .deliveryMan(deliveryMan)
                .pharmacist(pharmacist)
                .build();

        order.setPrescriptions(orderPrescriptions);

        return  orderMapper.toDTO(save(order));
    }
}
