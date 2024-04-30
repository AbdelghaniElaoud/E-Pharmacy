package com.epharmacy.app.service;

import com.epharmacy.app.dto.review.ChangeStatusReviewRequestDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import com.epharmacy.app.exceptions.ReviewAlreadyExistsException;
import com.epharmacy.app.exceptions.ReviewNotFoundException;
import com.epharmacy.app.mappers.ReviewMapper;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.Review;
import com.epharmacy.app.model.ReviewStatus;
import com.epharmacy.app.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    @Mock
    private ReviewRepository repository;

    @Autowired
    private CustomerService customerService;

    @Mock
    private DeliveryManService deliveryManService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ReviewService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Long id = 1L;
        Review review = new Review();
        when(repository.findById(id)).thenReturn(Optional.of(review));

        Optional<Review> result = service.findById(id);

        assertTrue(result.isPresent());
        assertEquals(review, result.get());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findAllValidReviewsForDeliveryMan() {
        Long deliveryManId = 1L;
        List<Review> reviews = Arrays.asList(
                new Review(),
                new Review(),
                new Review()
        );
        when(repository.findAllByDeliveryManIdAndStatus(deliveryManId, ReviewStatus.VALID)).thenReturn(reviews);

        List<Review> result = service.findAllValidReviewsForDeliveryMan(deliveryManId);

        assertEquals(reviews, result);
        verify(repository, times(1)).findAllByDeliveryManIdAndStatus(deliveryManId, ReviewStatus.VALID);
    }




    @Test
    void createNewReviewThrowsException() {
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();
        reviewRequestDTO.setOrderId(1L);
        reviewRequestDTO.setCustomerId(1L);
        reviewRequestDTO.setDeliveryManId(1L);

        when(repository.existsByCustomerIdAndOrderIdAndDeliveryManId(
                reviewRequestDTO.getCustomerId(),
                reviewRequestDTO.getOrderId(),
                reviewRequestDTO.getDeliveryManId()
        )).thenReturn(true);

        assertThrows(ReviewAlreadyExistsException.class, () -> service.createNewReview(reviewRequestDTO));
    }

    @Test
    void changeStatus() {
        ChangeStatusReviewRequestDTO changeStatusReviewRequestDTO = new ChangeStatusReviewRequestDTO();
        changeStatusReviewRequestDTO.setId(1L);
        changeStatusReviewRequestDTO.setStatus(ReviewStatus.VALID);

        Review review = new Review();
        when(repository.findById(changeStatusReviewRequestDTO.getId())).thenReturn(Optional.of(review));

        service.changeStatus(changeStatusReviewRequestDTO);

        verify(repository, times(1)).save(review);
        assertEquals(ReviewStatus.VALID, review.getStatus());
    }

    @Test
    void changeStatusThrowsException() {
        ChangeStatusReviewRequestDTO changeStatusReviewRequestDTO = new ChangeStatusReviewRequestDTO();
        changeStatusReviewRequestDTO.setId(1L);
        changeStatusReviewRequestDTO.setStatus(ReviewStatus.VALID);

        when(repository.findById(changeStatusReviewRequestDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> service.changeStatus(changeStatusReviewRequestDTO));
    }
    private Customer createCustomer() {
        Customer customer = Customer.builder()
                .address("9 rue albert bailly, Marcq-en-baroeul")
                .phone("0744240259")
                .balance(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP))
                .build();
        customer.setFirstName("Jaouad");
        customer.setUsername("jaouadel");
        customer.setLastName("El aoud");
        customer.setPassword("1234");
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
        customer.setEmail("jaouad.elaoud@gmail.com");

        try {
            // Save the customer using the customer service
            return customerService.save(customer);
        } catch (Exception e) {
            // Handle any exceptions during customer creation and saving
            e.printStackTrace();
            return null; // Return null in case of failure
        }
    }


}