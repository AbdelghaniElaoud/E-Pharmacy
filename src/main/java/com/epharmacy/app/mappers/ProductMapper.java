package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.product.ProductDTO;

import com.epharmacy.app.dto.product.ProductDTOAdministration;
import com.epharmacy.app.exceptions.CategoryNotFoundException;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.model.Media;
import com.epharmacy.app.model.Product;
import com.epharmacy.app.service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "medias", target = "medias", ignore = true)
    @Mapping(source = "category", target = "category", ignore = true)
    ProductDTO toDTO(Product product, @MappingTarget ProductDTO productDTO);

    @Mapping(source = "medias", target = "medias", ignore = true)
    @Mapping(source = "category", target = "category", ignore = true)
    Product toModel(ProductDTO productDTO, @MappingTarget Product product, @Context CategoryService categoryService);

    @Mapping(source = "category", target = "category")
    ProductDTOAdministration toProductDTOAdministration(Product product);

    @AfterMapping
    default void afterToDTO(Product product, @MappingTarget ProductDTO productDTO) {
        List<String> medias = CollectionUtils.emptyIfNull(product.getMedias()).stream()
                .map(Media::getLink)
                .toList();
        productDTO.setMedias(medias);
        productDTO.setCategory(CategoryMapper.INSTANCE.convert(product.getCategory()));
    }

    @AfterMapping
    default void afterToModel(ProductDTO productDTO, @MappingTarget Product product , @Context CategoryService categoryService) {
        Long categoryId = productDTO.getCategory().getId();
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        if (categoryOptional.isEmpty()){
            throw new CategoryNotFoundException(categoryId);
        }
        product.setCategory(categoryOptional.get());
    }

    default List<ProductDTO> convertAll(List<Product> products){
        return products.stream().map(product -> toDTO(product, new ProductDTO())).toList();
    }

    default ProductDTO convert(Product item){
        return toDTO(item, new ProductDTO());
    }

    default Product convert(ProductDTO item, CategoryService categoryService){
        return toModel(item, new Product(), categoryService);
    }

    default List<ProductDTOAdministration> convertAllToAdmin(List<Product> products){
        return products.stream().map(this::toProductDTOAdministration).toList();
    }
}
