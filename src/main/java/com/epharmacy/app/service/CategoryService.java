package com.epharmacy.app.service;

import com.epharmacy.app.dto.category.CategoryDTO;
import com.epharmacy.app.dto.category.CategoryDTO1;
import com.epharmacy.app.mappers.CategoryMapper;
import com.epharmacy.app.mappers.CategoryMapper1;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<CategoryDTO1> getAllCategories(){
        List<Category> categories = repository.findAll();
        List<CategoryDTO1> categoryDTO1List = new ArrayList<>();
        for (Category category: categories) {
            CategoryMapper1 categoryMapper1 = new CategoryMapper1(repository);
            CategoryDTO1 category1 = categoryMapper1.toDTO(category);
            categoryDTO1List.add(category1);
        }
        return categoryDTO1List;
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
