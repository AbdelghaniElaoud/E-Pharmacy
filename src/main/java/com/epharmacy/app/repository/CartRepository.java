package com.epharmacy.app.repository;

import com.epharmacy.app.model.Cart;
import com.epharmacy.app.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByActiveTrueAndId(Long id);
}
