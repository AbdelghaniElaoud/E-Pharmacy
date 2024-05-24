package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.address.AddressDTO;
import com.epharmacy.app.dto.cart.CartDTO;
import com.epharmacy.app.dto.cart.CartId;
import com.epharmacy.app.dto.cart.CreateCartDTO;
import com.epharmacy.app.dto.cartitem.CartItemRequestDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.dto.prescription.PrescriptionRequestDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.exceptions.CustomerAlreadyHasCart;
import com.epharmacy.app.mappers.CartMapper;
import com.epharmacy.app.model.Cart;
import com.epharmacy.app.repository.CartRepository;
import com.epharmacy.app.repository.UserRepository;
import com.epharmacy.app.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/carts")
@Slf4j
public class CartController {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public CartController(CartService cartService,
                          CartRepository cartRepository,
                          UserRepository userRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add-item")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartItemRequestDTO request) {
        ResponseDTO response = cartService.addToCart(request.getCartId(), request.getProductId(), request.getQuantity());
        return new ResponseEntity<>(response, response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/remove-item")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> removeFromCart(@RequestBody CartItemRequestDTO request) {
        ResponseDTO response = cartService.removeFromCart(request.getCartId(), request.getProductId());
        return new ResponseEntity<>(response, response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update-item")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateCartItem(@RequestBody CartItemRequestDTO request) {
        ResponseDTO response = cartService.updateCartItem(request.getCartId(), request.getProductId(), request.getQuantity());
        return new ResponseEntity<>(response, response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    /*@PostMapping("/add-item")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDTO addToCart(@RequestBody CartItemRequestDTO cartItem) {
        return cartService.addToCart(cartItem);
    }*/

    @PostMapping("/add-address")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDTO addAddress(@RequestBody AddressDTO addressDTO) {
        return CartMapper.INSTANCE.convert(cartService.addAddress(addressDTO));
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDTO create(@RequestBody CreateCartDTO createCartDTO) {
        ArrayList<Cart> cart = cartRepository.findCartByCustomerId(createCartDTO.getCustomerId());
        if (!cart.isEmpty()){
            return ResponseDTO.builder().errors(List.of("The user has already an active cart")).build();
        }
        try {
            return ResponseDTO.builder().ok(true).content(CartMapper.INSTANCE.convert(cartService.createNewCart(createCartDTO.getCustomerId()))).build();
        } catch (Exception e) {
            log.error("Could not create cart for customer {}", createCartDTO.getCustomerId());
            return ResponseDTO.builder().errors(List.of(e.getMessage())).build();
        }
    }

    @GetMapping("/get-cart/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDTO getActiveCartOfCustomer(@PathVariable(name = "customerId") long customerId){
        Long Id = cartRepository.findActiveCartIdByCustomerId(customerId);
        CartId cartId = new CartId();
        if (Id == null){
            return ResponseDTO.builder().ok(false).errors(List.of("The user has no active cart")).build();
        }
        cartId.setId(Id);
        return ResponseDTO.builder().ok(true).content(cartId).build();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseDTO getCart(@PathVariable Long id) {
        Optional<Cart> cartOptional = cartService.findById(id);
        if (cartOptional.isEmpty()) {
            return ResponseDTO.builder().errors(List.of("Could not find cart for by id " + id)).build();
        }
        return ResponseDTO.builder().ok(true).content(CartMapper.INSTANCE.convert(cartOptional.get())).build();
    }

    @PostMapping("{cartId}/add-prescription")
    @PreAuthorize("hasRole('CUSTOMER')")
    public PrescriptionDTO addToCart(@PathVariable Long cartId, @RequestPart PrescriptionRequestDTO prescriptionRequestDTO, @RequestPart MultipartFile file) {
        return cartService.addPrescription(cartId,prescriptionRequestDTO,file);
    }

}
