package com.epharmacy.app.service;

import com.epharmacy.app.model.OrderItem;
import com.epharmacy.app.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Optional<OrderItem> findById(Long id){
        return orderItemRepository.findById(id);
    }

    public OrderItem save(OrderItem item){
        return orderItemRepository.save(item);
    }
    public void delete(OrderItem item){
        orderItemRepository.delete(item);
    }
    public void delete(Iterable<OrderItem> items){
        orderItemRepository.deleteAll(items);
    }
    public void deleteById(Long id){
        orderItemRepository.deleteById(id);
    }
}
