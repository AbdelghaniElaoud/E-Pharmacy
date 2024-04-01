package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{cartId}/place-order")
    public ResponseDTO placeOrder(@PathVariable Long cartId){
        return orderService.placeOrder(cartId);
    }
    @PutMapping("/{orderId}/update-status")
    public ResponseEntity<HttpStatus> changeOrderStatus(@PathVariable Long orderId, @RequestBody String status){
        orderService.updateStatus(orderId,status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{deliveryManId}/all-orders")
    public List<OrderDTO> getAllOrdersByDeliveryManId(@PathVariable Long deliveryManId){
        return orderService.getAllOrdersByDeliveryManId(deliveryManId);
    }

    @GetMapping("{customerId}/all-canceled-orders")
    public List<OrderDTO> getAllCanceledOrders(@PathVariable Long customerId){
        return orderService.getAllCanceledOrders(customerId);
    }


}
