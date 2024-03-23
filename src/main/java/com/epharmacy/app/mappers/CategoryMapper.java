package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.category.CategoryDTO;
import com.epharmacy.app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CategoryDTO toDTO(Category source, @MappingTarget CategoryDTO target);
    Category toModel(CategoryDTO source, @MappingTarget Category target);

    public default List<CategoryDTO> convertAll(List<Category> items){
        return items.stream().map(category -> toDTO(category, new CategoryDTO())).toList();
    }

    public default CategoryDTO convert(Category source){
        return toDTO(source, new CategoryDTO());
    }
    public default Category convert(CategoryDTO source){
        return toModel(source, new Category());
    }
}