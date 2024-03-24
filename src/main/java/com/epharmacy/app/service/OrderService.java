package com.epharmacy.app.service;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.enums.PaymentStatus;
import com.epharmacy.app.exceptions.DeliveryManNotFoundException;
import com.epharmacy.app.exceptions.PharmacistNotFoundException;
import com.epharmacy.app.mappers.OrderMapper;
import com.epharmacy.app.model.*;
import com.epharmacy.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final DeliveryManRepository deliveryManRepository;
    private final PharmacistRepository pharmacistRepository;
    private final OrderItemRepository orderItemRepository;
    private final PrescriptionRepository prescriptionRepository ;


    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, DeliveryManRepository deliveryManRepository, PharmacistRepository pharmacistRepository, OrderItemRepository orderItemRepository, PrescriptionRepository prescriptionRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.deliveryManRepository = deliveryManRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.orderItemRepository = orderItemRepository;
        this.prescriptionRepository = prescriptionRepository;
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

    public ResponseDTO placeOrder(Long cartId) {
        Optional<Cart> cartOptional = cartService.findById(cartId);
        ResponseDTO.ResponseDTOBuilder responseDTOBuilder = ResponseDTO.builder();
        if (cartOptional.isEmpty()){
            return responseDTOBuilder.ok(false).errors(List.of("Could not find your cart")).build();
        }

        Cart cart = cartOptional.get();
        boolean noPrescription = cart.getPrescriptions().isEmpty();
        for (CartItem cartItem : cart.getEntries()){
            if(cartItem.getAddedProduct().isPrescription() && noPrescription){
                return responseDTOBuilder.ok(false).errors(List.of("You have some products in your cart that need prescription, please attach to the cart.")).build();
            }
        }

        List<OrderItem> orderItems = cart.getEntries().stream().map(
                cartItem -> orderItemRepository.save(OrderItem.builder()
                        .orderedProduct(cartItem.getAddedProduct())
                        .discount(cartItem.getDiscount())
                        .quantity(cartItem.getQuantity())
                        .totalPrice(cartItem.getTotalPrice())
                        .basePrice(cartItem.getBasePrice())
                        .build())
        ).collect(Collectors.toCollection(ArrayList::new));

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
                .address(cart.getCustomer().getAddress())
                .entries(orderItems)
                .orderStatus(OrderStatus.INIT)
                .totalPrice(cart.getTotalPrice())
                .paymentStatus(PaymentStatus.WAITING)
                .customer(cart.getCustomer())
                .totalPrice(cart.getTotalPrice())
                .deliveryMan(deliveryMan)
                .pharmacist(pharmacist)
                .build();
        Order savedOrder = save(order);
        savedOrder.setPrescriptions(cart.getPrescriptions().stream().map(prescription -> {
            prescription.setId(null);
            prescription.setOrder(savedOrder);
            return prescriptionRepository.save(prescription);
        }).collect(Collectors.toSet()));

        cart.setActive(false);
        cartService.save(cart);
        OrderDTO dto = OrderMapper.INSTANCE.toDTO(save(savedOrder));
        dto.setNewCartId(cartService.createNewCart(order.getCustomer().getId()).getId());
        return responseDTOBuilder.content(dto).ok(true).build();
    }
}
