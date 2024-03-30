package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.address.AddressDTO;
import com.epharmacy.app.dto.cart.CartDTO;
import com.epharmacy.app.dto.cartitem.CartItemRequestDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.dto.prescription.PrescriptionRequestDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.mappers.CartMapper;
import com.epharmacy.app.model.Cart;
import com.epharmacy.app.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
@Slf4j
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/add-item")
    public ResponseDTO addToCart(@RequestBody CartItemRequestDTO cartItem) {
        return cartService.addToCart(cartItem);
    }

    @PostMapping("/add-address")
    public CartDTO addAddress(@RequestBody AddressDTO addressDTO) {
        return CartMapper.INSTANCE.convert(cartService.addAddress(addressDTO));
    }

    @PostMapping
    public ResponseDTO create(@RequestBody CartItemRequestDTO cartItem) {
        try {
            return ResponseDTO.builder().ok(true).content(CartMapper.INSTANCE.convert(cartService.createNewCart(cartItem.getCustomerId()))).build();
        } catch (Exception e) {
            log.error("Could not create cart for customer {}", cartItem.getCustomerId());
            return ResponseDTO.builder().errors(List.of(e.getMessage())).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseDTO getCart(@PathVariable Long id) {
        Optional<Cart> cartOptional = cartService.findById(id);
        if (cartOptional.isEmpty()) {
            return ResponseDTO.builder().errors(List.of("Could not find cart for by id " + id)).build();
        }
        return ResponseDTO.builder().ok(true).content(CartMapper.INSTANCE.convert(cartOptional.get())).build();
    }

    @PostMapping("{cartId}/add-prescription")
    public PrescriptionDTO addToCart(@PathVariable Long cartId, @RequestPart PrescriptionRequestDTO prescriptionRequestDTO, @RequestPart MultipartFile file) {
        return cartService.addPrescription(cartId,prescriptionRequestDTO,file);
    }

}
