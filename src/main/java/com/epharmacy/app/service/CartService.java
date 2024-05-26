package com.epharmacy.app.service;

import com.epharmacy.app.dto.address.AddressDTO;
import com.epharmacy.app.dto.cartitem.CartItemRequestDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.dto.prescription.PrescriptionRequestDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.exceptions.*;
import com.epharmacy.app.mappers.CartMapper;
import com.epharmacy.app.mappers.PrescriptionMapper;
import com.epharmacy.app.model.*;
import com.epharmacy.app.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CartService {
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final MediaService mediaService;
    private final PrescriptionRepository prescriptionRepository;

    private final OrderRepository orderRepository;

    public CartService(CartRepository repository, CartItemRepository cartItemRepository, ProductService productService, CustomerService customerService, MediaService mediaService,
                       PrescriptionRepository prescriptionRepository,
                       MediaRepository mediaRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.repository = repository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.customerService = customerService;
        this.mediaService = mediaService;
        this.prescriptionRepository = prescriptionRepository;
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    public ResponseDTO getCartByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        ResponseDTO.ResponseDTOBuilder builder = ResponseDTO.builder();
        if (userOptional.isEmpty()) {
            return builder.errors(List.of("User not found")).ok(false).build();
        }

        Long cartId = repository.findActiveCartIdByCustomerId(userId);
        if (cartId == null) {
            return builder.errors(List.of("No active cart found for user")).ok(false).build();
        }

        Optional<Cart> cartOptional = repository.findById(cartId);
        if (cartOptional.isEmpty()) {
            return builder.errors(List.of("Cart not found")).ok(false).build();
        }

        Cart cart = cartOptional.get();
        return builder.ok(true).content(cart).build();
    }

    public ResponseDTO addToCart(Long cartId, Long productId, Long quantity) {
        Optional<Cart> cartOptional = findById(cartId);
        ResponseDTO.ResponseDTOBuilder builder = ResponseDTO.builder();
        if (cartOptional.isEmpty()) {
            return builder.errors(List.of("Could not find your cart")).build();
        }
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isEmpty()) {
            return builder.errors(List.of("Could not find product " + productId)).build();
        }
        Cart cart = cartOptional.get();
        Product product = productOptional.get();
        CartItem cartItem = cart.getEntries().stream()
                .filter(item -> item.getAddedProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(quantity); // Update the quantity directly
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .discount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                    .addedProduct(product)
                    .basePrice(product.getPrice().setScale(2, RoundingMode.HALF_UP))
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP))
                    .quantity(quantity)
                    .build();
            cart.getEntries().add(cartItem);
        }

        cartItemRepository.save(cartItem);
        updateCartTotalPrice(cart);

        return builder.ok(true).content(CartMapper.INSTANCE.convert(save(cart))).build();
    }

    public ResponseDTO removeFromCart(Long cartId, Long productId) {
        Optional<Cart> cartOptional = findById(cartId);
        ResponseDTO.ResponseDTOBuilder builder = ResponseDTO.builder();
        if (cartOptional.isEmpty()) {
            return builder.errors(List.of("Could not find your cart")).build();
        }
        Cart cart = cartOptional.get();
        CartItem cartItem = cart.getEntries().stream()
                .filter(item -> item.getAddedProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cart.getEntries().remove(cartItem);
            cartItemRepository.delete(cartItem);
            updateCartTotalPrice(cart);
            return builder.ok(true).content(CartMapper.INSTANCE.convert(save(cart))).build();
        } else {
            return builder.errors(List.of("Could not find item in cart")).build();
        }
    }

    private void updateCartTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getEntries().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP));
    }

    public ResponseDTO updateCartItem(Long cartId, Long productId, Long quantity) {
        if (quantity <= 0) {
            return removeFromCart(cartId, productId);
        }
        return addToCart(cartId, productId, quantity);
    }

    public Cart createNewCart(Long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw  new CustomerNotFoundException(customerId);
        }
        Cart cart = Cart.builder().code(UUID.randomUUID().toString()).active(true).totalPrice(BigDecimal.ZERO).customer(customerOptional.get()).build();
        return save(cart);
    }
    /*public ResponseDTO addToCart(Long cartId, Long productId, Long quantity) {
        Optional<Cart> cartOptional = findById(cartId);
        ResponseDTO.ResponseDTOBuilder builder = ResponseDTO.builder();
        if (cartOptional.isEmpty()) {
            return builder.errors(List.of("Could not find your cart")).build();
        }
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isEmpty()) {
            return builder.errors(List.of("Could not find product " + productId)).build();
        }
        Cart cart = cartOptional.get();
        Product product = productOptional.get();

        // Check if the cart already contains the product
        Optional<CartItem> existingCartItemOptional = cart.getEntries().stream()
                .filter(entry -> entry.getAddedProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItemOptional.isPresent()) {
            // Update the existing cart item
            CartItem existingCartItem = existingCartItemOptional.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(existingCartItem.getBasePrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())).setScale(2, RoundingMode.HALF_UP));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .discount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                    .addedProduct(product)
                    .basePrice(product.getPrice().setScale(2, RoundingMode.HALF_UP))
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP))
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(cartItem);
            cart.getEntries().add(cartItem);
        }

        // Update the cart's total price
        cart.setTotalPrice(cart.getEntries().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP));
        return builder.ok(true).content(CartMapper.INSTANCE.convert(save(cart))).build();
    }*/


    public Optional<Cart> findById(Long id){
        return Optional.ofNullable(repository.findByActiveTrueAndId(id));
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

    public ResponseDTO addToCart(CartItemRequestDTO cartItem) {
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

    public PrescriptionDTO addPrescription(Long cartId, PrescriptionRequestDTO prescriptionRequestDTO, MultipartFile file) {
        Optional<Cart> cartOptional = repository.findById(cartId);
        if (cartOptional.isEmpty()){
            throw new CartNotFoundException(cartId);
        }
        boolean requiresPrescription = false;
        for (CartItem cartItem : cartOptional.get().getEntries()) {
            if (cartItem.getAddedProduct().isPrescription()) {
                requiresPrescription = true;
                break;
            }
        }

        if (!requiresPrescription) {
            throw new CartDoesntRequirePrescription(cartId);
        }

        try {
            String link = mediaService.uploadFile(file);
            Media media = Media.builder().link(link).altText("alt 1").build();
            mediaRepository.save(media);
            Optional<Customer> customerOptional = customerService.findById(prescriptionRequestDTO.getCustomerId());
            if (customerOptional.isEmpty()){
                throw new CustomerNotFoundException(prescriptionRequestDTO.getCustomerId());
            }
            Cart cart = cartOptional.get();
            List<Prescription> prescriptions = new ArrayList<>(CollectionUtils.emptyIfNull(cart.getPrescriptions()));
            Prescription prescription = Prescription.builder()
                    .cart(cart)
                    .date(new SimpleDateFormat("dd/MM/yyyy").parse(prescriptionRequestDTO.getDate()))
                    .doctor(prescriptionRequestDTO.getDoctor())
                    .media(media)
                    .customer(customerOptional.get())
                    .build();
            Prescription prescription1 = prescriptionRepository.save(prescription);
            media.setPrescription(prescription1);
            mediaRepository.save(media);
            prescriptions.add(prescription);
            return PrescriptionMapper.INSTANCE.toDTO(prescription);
        } catch (IOException e) {
            log.error("Could not attach media to cart {}", cartId, e);
        } catch (ParseException ex) {
            throw new TimeFormatIsNotValid();
        }
        return new PrescriptionDTO();
    }
}
