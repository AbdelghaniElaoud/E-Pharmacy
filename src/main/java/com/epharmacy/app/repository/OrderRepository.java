package com.epharmacy.app.repository;

import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByDeliveryMan_IdAndOrderStatusNot(Long deliveryManId, OrderStatus orderStatus);
    List<Order> getAllByDeliveryMan_Id(Long deliveryManId);
    List<Order> getAllByCustomer_IdAndOrderStatusIn(Long customerId, List<OrderStatus> orderStatuses);

    List<Order> findByPharmacistId(Long pharmacistId);
    List<Order> findByDeliveryManIdAndOrderStatusNotIn(Long deliveryManId, List<OrderStatus> orderStatuses);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.orderStatus <> 'COMPLETED'")
    List<Order> findIncompleteOrdersByCustomerId(@Param("customerId") Long customerId);
}
