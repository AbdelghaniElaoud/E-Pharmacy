package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{cartId}/place-order")
    public OrderDTO placeOrder(@PathVariable Long cartId){
        return orderService.placeOrder(cartId);
    }
}
