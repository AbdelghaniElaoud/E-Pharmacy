package com.epharmacy.app.service;

import com.epharmacy.app.model.Category;
import com.epharmacy.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> findById(Long id){
        return repository.findById(id);
    }
    public List<Category> getAllCategories(){
        return repository.findAll();
    }

    public Category save(Category item){
        return repository.save(item);
    }
    public void delete(Category item){
        repository.delete(item);
    }
    public void delete(Iterable<Category> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
