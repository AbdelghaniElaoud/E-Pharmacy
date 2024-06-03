package com.epharmacy.app.service;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.enums.PaymentStatus;
import com.epharmacy.app.exceptions.DeliveryManNotFoundException;
import com.epharmacy.app.exceptions.OrderNotFoundException;
import com.epharmacy.app.exceptions.PharmacistNotFoundException;
import com.epharmacy.app.mappers.OrderMapper;
import com.epharmacy.app.model.*;
import com.epharmacy.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final DeliveryManRepository deliveryManRepository;
    private final PharmacistRepository pharmacistRepository;
    private final OrderItemRepository orderItemRepository;
    private final PrescriptionRepository prescriptionRepository ;
    private final ProductRepository productRepository ;
    private final CartItemRepository cartItemRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, DeliveryManRepository deliveryManRepository, PharmacistRepository pharmacistRepository, OrderItemRepository orderItemRepository, PrescriptionRepository prescriptionRepository, ProductRepository productRepository,
                        CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.deliveryManRepository = deliveryManRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.orderItemRepository = orderItemRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
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

        boolean IsThereNoCartItems = cartItemRepository.findAllByCart(cartOptional.get()).isEmpty();

        if (IsThereNoCartItems){
            return responseDTOBuilder.ok(false).errors(List.of("Your cart is empty please add something to it")).build();
        }

        Cart cart = cartOptional.get();
        boolean noPrescription = cart.getPrescriptions().isEmpty();
        for (CartItem cartItem : cart.getEntries()){
            if(cartItem.getAddedProduct().isPrescription() && noPrescription){
                return responseDTOBuilder.ok(false).errors(List.of("You have some products in your cart that need prescription, please attach to the cart.")).build();
            }
        }

        for (CartItem cartItem : cart.getEntries()) {
            Optional<Product> optionalProduct = productRepository.findById(cartItem.getAddedProduct().getId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                long remainInStock = product.getStock() - cartItem.getQuantity();
                if (remainInStock >= 0) {
                    product.setStock(remainInStock);
                    productRepository.save(product);
                } else {
                    return responseDTOBuilder
                            .ok(false)
                            .errors(List.of(String.format("%s still in stock %s items", product.getName(), product.getStock())))
                            .build();
                }
            } else {
                return responseDTOBuilder
                        .ok(false)
                        .errors(List.of("Product not found"))
                        .build();
            }
        }


        if (cartOptional.get().getAddress() == null){
            return responseDTOBuilder.ok(false).errors(List.of("Cannot find the delivery Address, please add it!")).build();
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
//            prescription.setId(null);
//            prescription.setOrder(savedOrder);
            return prescriptionRepository.save(prescription);
        }).collect(Collectors.toSet()));

        cart.setActive(false);
        cartService.save(cart);
        OrderDTO dto = OrderMapper.INSTANCE.toDTO(save(savedOrder));
        dto.setNewCartId(cartService.createNewCart(order.getCustomer().getId()).getId());
        return responseDTOBuilder.content(dto).ok(true).build();
    }

    public void updateStatus(Long orderId, String status) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException(orderId);
        }
        orderOptional.get().setOrderStatus(OrderStatus.valueOf(status));
        save(orderOptional.get());
    }

    public List<OrderDTO> getAllOrdersByDeliveryManId(Long deliveryManId) {
        List<Order> orders = orderRepository.getAllByDeliveryMan_IdAndOrderStatusNot(deliveryManId,OrderStatus.CANCELED);
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order:orders) {
               orderDTOS.add(OrderMapper.INSTANCE.toDTO(order));
        }
        return orderDTOS;
    }

    public List<OrderDTO> getAllCanceledOrders(Long customerId) {
        List<Order> orders = orderRepository.getAllByCustomer_IdAndOrderStatusIn(customerId, Arrays.asList(OrderStatus.CANCELED,OrderStatus.PRESCRIPTION_REFUSED));
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order:orders) {
            orderDTOS.add(OrderMapper.INSTANCE.toDTO(order));
        }
        return orderDTOS;
    }

    public List<OrderDTO> getAllOrdersByPharmacistId(Long pharmacistId) {
        List<Order> orders = orderRepository.getAllByDeliveryMan_Id(pharmacistId);
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order:orders) {
            orderDTOS.add(OrderMapper.INSTANCE.toDTO(order));
        }
        return orderDTOS;
    }

    public List<OrderDTO> getOrdersByPharmacist(Long pharmacistId) {
        List<Order> orders = orderRepository.findByPharmacistId(pharmacistId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(OrderMapper.INSTANCE.toDTO(order));
        }
        return orderDTOS;
    }

    public List<OrderDTO> getOrdersForDelivery(Long deliveryManId) {
        List<OrderStatus> excludedStatuses = Arrays.asList(OrderStatus.INIT, OrderStatus.CANCELED, OrderStatus.PRESCRIPTION_REFUSED, OrderStatus.ISSUE, OrderStatus.COMPLETED);
        List<Order> orders = orderRepository.findByDeliveryManIdAndOrderStatusNotIn(deliveryManId, excludedStatuses);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(OrderMapper.INSTANCE.toDTO(order));
        }
        return orderDTOS;
    }

    public Set<Prescription> getPrescriptionsByOrderId(Long orderId) {
        return orderRepository.findById(orderId).get().getPrescriptions();
    }

    public void delivered(Long orderId) {

        Order order = orderRepository.findById(orderId).get();
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setPaymentStatus(PaymentStatus.PAID);

        orderRepository.save(order);
    }

    public void issue(Long orderId) {

        Order order = orderRepository.findById(orderId).get();
        order.setOrderStatus(OrderStatus.ISSUE);
        order.setPaymentStatus(PaymentStatus.NOT_PAID);

        orderRepository.save(order);

    }

    public List<Order> findIncompleteOrdersByCustomerId(Long customerId) {
        return orderRepository.findIncompleteOrdersByCustomerId(customerId);
    }
}
