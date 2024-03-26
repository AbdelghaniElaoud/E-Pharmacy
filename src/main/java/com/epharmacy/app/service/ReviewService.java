package com.epharmacy.app.service;

import com.epharmacy.app.model.Product;
import com.epharmacy.app.model.Review;
import com.epharmacy.app.repository.ProductRepository;
import com.epharmacy.app.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Optional<Review> findById(Long id){
        return repository.findById(id);
    }

    public Review save(Review item){
        return repository.save(item);
    }
    public void delete(Review item){
        repository.delete(item);
    }
    public void delete(Iterable<Review> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
