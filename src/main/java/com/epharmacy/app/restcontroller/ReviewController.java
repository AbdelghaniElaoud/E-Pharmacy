package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.review.ReviewDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.mappers.ReviewMapper;
import com.epharmacy.app.model.Review;
import com.epharmacy.app.service.CustomerService;
import com.epharmacy.app.service.DeliveryManService;
import com.epharmacy.app.service.OrderService;
import com.epharmacy.app.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {


    private final CustomerService customerService;
    private final ReviewService reviewService;
    private final DeliveryManService deliveryManService;
    private final OrderService orderService;


    public ReviewController(CustomerService customerService, ReviewService reviewService, DeliveryManService deliveryManService, OrderService orderService) {
        this.customerService = customerService;
        this.reviewService = reviewService;
        this.deliveryManService = deliveryManService;
        this.orderService = orderService;
    }


    @PostMapping("")
    public ReviewDTO addDReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        Review review = ReviewMapper.INSTANCE.toModel(reviewRequestDTO);
        ReviewMapper.INSTANCE.afterToModel(reviewRequestDTO,review,customerService,deliveryManService,orderService);
        Review reviewSaved = reviewService.save(review);
        ReviewDTO reviewDTO = ReviewMapper.INSTANCE.toDTO(reviewSaved);
        ReviewMapper.INSTANCE.afterToDTO(reviewSaved,reviewDTO);
        return reviewDTO;
    }
}
