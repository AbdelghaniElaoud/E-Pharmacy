package com.epharmacy.app.repository;

import com.epharmacy.app.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByActiveTrueAndId(Long id);
    ArrayList<Cart> findCartByCustomerId(Long customerId);
    @Query("SELECT c.id FROM Cart c WHERE c.customer.id = :customerId AND c.active = true")
    Long findActiveCartIdByCustomerId(@Param("customerId") Long customerId);
}
