package com.epharmacy.app.repository;

import com.epharmacy.app.model.Review;
import com.epharmacy.app.model.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByDeliveryManIdAndStatus(Long id, ReviewStatus status);
    boolean existsByCustomerIdAndOrderIdAndDeliveryManId(Long customerId, Long orderId, Long dManId);
}
