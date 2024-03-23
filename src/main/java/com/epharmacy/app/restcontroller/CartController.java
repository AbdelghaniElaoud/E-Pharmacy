package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.address.AddressDTO;
import com.epharmacy.app.dto.cart.CartDTO;
import com.epharmacy.app.dto.cartitem.CartItemRequestDTO;
import com.epharmacy.app.exceptions.CustomerNotFoundException;
import com.epharmacy.app.mappers.CartMapper;
import com.epharmacy.app.model.Cart;
import com.epharmacy.app.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/add-item")
    public CartDTO addToCart(@RequestBody CartItemRequestDTO cartItem){
       return CartMapper.INSTANCE.convert(cartService.addToCart(cartItem));
    }
    @PostMapping("/add-address")
    public CartDTO addAddress(@RequestBody AddressDTO addressDTO){
        return CartMapper.INSTANCE.convert(cartService.addAddress(addressDTO));
    }
    @PostMapping("")
    public CartDTO create(@RequestBody CartItemRequestDTO cartItem){
        return CartMapper.INSTANCE.convert(cartService.createNewCart(cartItem.getCustomerId()));
    }
    @GetMapping("/{id}")
    public CartDTO getCard(@PathVariable Long id){
        Optional<Cart> cartOptional = cartService.findById(id);
        if (cartOptional.isEmpty()){
            throw new CustomerNotFoundException(id);
        }
        return CartMapper.INSTANCE.convert(cartOptional.get());
    }
}
