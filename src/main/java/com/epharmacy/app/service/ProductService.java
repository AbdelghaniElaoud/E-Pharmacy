package com.epharmacy.app.service;

import com.epharmacy.app.model.Product;
import com.epharmacy.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> findById(Long id){
        return repository.findById(id);
    }
    public List<Product> getAllActiveProducts(){
        return repository.findAllByActiveTrue();
    }

    public Product save(Product item){
        return repository.save(item);
    }
    public void delete(Product item){
        repository.delete(item);
    }
    public void delete(Iterable<Product> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
