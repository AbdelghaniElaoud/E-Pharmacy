package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.category.CategoryDTO;
import com.epharmacy.app.mappers.CategoryMapper;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('PHARMACIST')")
    public List<CategoryDTO> getAllCategories(){
        List<Category> allActiveProducts = categoryService.getAllCategories();
        return CategoryMapper.INSTANCE.convertAll(allActiveProducts);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = CategoryMapper.INSTANCE.convert(categoryDTO);
        return CategoryMapper.INSTANCE.convert(categoryService.save(category));
    }
}
