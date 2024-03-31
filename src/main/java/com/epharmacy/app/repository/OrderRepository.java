package com.epharmacy.app.repository;

import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByDeliveryMan_IdAndOrderStatusNot(Long deliveryManId, OrderStatus orderStatus);
}
