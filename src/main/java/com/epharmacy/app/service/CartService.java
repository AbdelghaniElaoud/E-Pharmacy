package com.epharmacy.app.service;

import com.epharmacy.app.dto.address.AddressDTO;
import com.epharmacy.app.dto.cartitem.CartItemRequestDTO;
import com.epharmacy.app.exceptions.CartNotFoundException;
import com.epharmacy.app.exceptions.CustomerNotFoundException;
import com.epharmacy.app.exceptions.ProductNotFoundException;
import com.epharmacy.app.model.Cart;
import com.epharmacy.app.model.CartItem;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.Product;
import com.epharmacy.app.repository.CartItemRepository;
import com.epharmacy.app.repository.CartRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public CartService(CartRepository repository, CartItemRepository cartItemRepository, ProductService productService, CustomerService customerService) {
        this.repository = repository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Cart createNewCart(Long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw  new CustomerNotFoundException(customerId);
        }
        Cart cart = Cart.builder().code(UUID.randomUUID().toString()).totalPrice(BigDecimal.ZERO).customer(customerOptional.get()).build();
        return save(cart);
    }
    public Cart addToCart(Long cartId, Long productId, Long quantity){
        Optional<Cart> cartOptional = findById(cartId);
        if (cartOptional.isEmpty()){
            throw new CartNotFoundException(cartId);
        }
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException(productId);
        }
        Cart cart = cartOptional.get();
        Product product = productOptional.get();
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .discount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .addedProduct(product)
                .basePrice(product.getPrice().setScale(2, RoundingMode.HALF_UP))
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP))
                .quantity(quantity)
                .build();
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        List<CartItem> entries = new ArrayList<>(CollectionUtils.emptyIfNull(cart.getEntries()));
        entries.removeIf(entry-> entry.getAddedProduct().getId().equals(productId));
        entries.add(savedCartItem);
        cart.setEntries(entries);
        cart.setTotalPrice(entries.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
        return repository.save(cart);
    }

    public Optional<Cart> findById(Long id){
        return repository.findById(id);
    }

    public Cart save(Cart item){
        return repository.save(item);
    }
    public void delete(Cart item){
        repository.delete(item);
    }
    public void delete(Iterable<Cart> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Cart addToCart(CartItemRequestDTO cartItem) {
        return addToCart(cartItem.getCartId(), cartItem.getProductId(), cartItem.getQuantity());
    }

    public Cart addAddress(AddressDTO addressDTO) {
        Optional<Cart> cartOptional = findById(addressDTO.getCartId());
        if (cartOptional.isEmpty()){
            throw new CartNotFoundException(addressDTO.getCartId());
        }
        Cart cart = cartOptional.get();
        cart.setAddress(addressDTO.getLine());
        return save(cart);
    }
}
