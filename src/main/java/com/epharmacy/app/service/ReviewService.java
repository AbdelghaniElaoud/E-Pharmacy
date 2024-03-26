package com.epharmacy.app.service;

import com.epharmacy.app.dto.review.ChangeStatusReviewRequestDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.exceptions.ReviewAlreadyExistsException;
import com.epharmacy.app.exceptions.ReviewNotFoundException;
import com.epharmacy.app.mappers.ReviewMapper;
import com.epharmacy.app.model.Review;
import com.epharmacy.app.model.ReviewStatus;
import com.epharmacy.app.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final CustomerService customerService;
    private final DeliveryManService deliveryManService;
    private final OrderService orderService;

    public ReviewService(ReviewRepository repository, CustomerService customerService, DeliveryManService deliveryManService, OrderService orderService) {
        this.repository = repository;
        this.customerService = customerService;
        this.deliveryManService = deliveryManService;
        this.orderService = orderService;
    }

    public Optional<Review> findById(Long id) {
        return repository.findById(id);
    }

    public List<Review> findAllValidReviewsForDeliveryMan(Long id) {
        return repository.findAllByDeliveryManIdAndStatus(id, ReviewStatus.VALID);
    }

    public Review save(Review item) {
        return repository.save(item);
    }

    public void delete(Review item) {
        repository.delete(item);
    }

    public void delete(Iterable<Review> items) {
        repository.deleteAll(items);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void createNewReview(ReviewRequestDTO reviewRequestDTO) {
        if(reviewExists(reviewRequestDTO)){
            throw new ReviewAlreadyExistsException(reviewRequestDTO.getOrderId());
        }
        Review review = ReviewMapper.INSTANCE.toModel(reviewRequestDTO, customerService, deliveryManService, orderService);
        if (reviewRequestDTO.getId() == null) {
            review.setStatus(ReviewStatus.CREATED);
        }
        save(review);
    }

    private boolean reviewExists(ReviewRequestDTO reviewRequestDTO) {
        return repository.existsByCustomerIdAndOrderIdAndDeliveryManId(reviewRequestDTO.getCustomerId(), reviewRequestDTO.getOrderId(), reviewRequestDTO.getDeliveryManId());
    }

    public void changeStatus(ChangeStatusReviewRequestDTO changeStatusReviewRequestDTO) {
        Optional<Review> reviewOptional = findById(changeStatusReviewRequestDTO.getId());
        if (reviewOptional.isEmpty()) {
            throw new ReviewNotFoundException(changeStatusReviewRequestDTO.getId());
        }
        Review review = reviewOptional.get();
        review.setStatus(changeStatusReviewRequestDTO.getStatus());
        save(review);
    }
}
