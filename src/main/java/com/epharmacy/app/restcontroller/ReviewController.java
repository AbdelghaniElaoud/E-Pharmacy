package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.review.ChangeStatusReviewRequestDTO;
import com.epharmacy.app.dto.review.ResponseReviewDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.mappers.ReviewMapper;
import com.epharmacy.app.model.Review;
import com.epharmacy.app.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("")
    public ResponseEntity addDReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.createNewReview(reviewRequestDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{deliveryManId}")
    public List<ResponseReviewDTO> getAllValidReviews(@PathVariable Long deliveryManId) {
        List<Review> reviews = reviewService.findAllValidReviewsForDeliveryMan(deliveryManId);
        return  ReviewMapper.INSTANCE.toDTOResponseList(reviews);
    }

    @GetMapping("/change-status")
    public ResponseEntity changeStatus(@RequestBody ChangeStatusReviewRequestDTO changeStatusReviewRequestDTO) {
        reviewService.changeStatus(changeStatusReviewRequestDTO);
        return ResponseEntity.ok().build();
    }
}
