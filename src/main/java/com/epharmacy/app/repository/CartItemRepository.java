package com.epharmacy.app.repository;

import com.epharmacy.app.model.Cart;
import com.epharmacy.app.model.CartItem;
import com.epharmacy.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> getCartItemByAddedProduct_Id(Long productId);
    List<CartItem> findAllByCart(Cart cart);
}
