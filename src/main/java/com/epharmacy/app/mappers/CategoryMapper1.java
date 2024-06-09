package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.category.CategoryDTO1;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryMapper1 {

    private final CategoryRepository categoryRepository;

    public CategoryMapper1(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public  CategoryDTO1 toDTO(Category category){
        CategoryDTO1 categoryDTO = new CategoryDTO1();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setProductNumbers(categoryRepository.countProductsByCategoryId(category.getId()) + 1);

        return categoryDTO;

    }
}
