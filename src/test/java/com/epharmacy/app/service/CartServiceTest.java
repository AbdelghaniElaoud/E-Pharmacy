package com.epharmacy.app.service;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import com.epharmacy.app.model.Cart;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.repository.CartItemRepository;
import com.epharmacy.app.repository.CartRepository;
import com.epharmacy.app.repository.MediaRepository;
import com.epharmacy.app.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @Mock
    private MediaService mediaService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private MediaRepository mediaRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository, cartItemRepository, productService, customerService, mediaService, prescriptionRepository, mediaRepository);

        when(customerService.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testCreateNewCart() {
        Customer customer = Customer.builder()
                .address("9 rue albert bailly, Marcq-en-baroeul")
                .phone("0744240259")
                .balance(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP))
                .build();
        customer.setFirstName("Jaouad");
        customer.setUsername("jaouadel");
        customer.setLastName("El aoud");
        customer.setPassword("1234");
        customer.setRole(UserRole.ROLE_CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
        customer.setEmail("jaouad.elaoud@gmail.com");
        Customer savedCustomer = customerService.save(customer);
        when(customerService.findById(savedCustomer.getId())).thenReturn(Optional.of(customer));

        Cart cart = cartService.createNewCart(savedCustomer.getId());

        assertEquals(customer, cart.getCustomer());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
        verify(cartRepository, times(1)).save(cart);
    }

    // Add more test cases for other methods in CartService as needed

}