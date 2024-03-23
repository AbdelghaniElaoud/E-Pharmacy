package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.cartitem.CartItemDTO;
import com.epharmacy.app.model.CartItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);


    @Mapping(source = "addedProduct", target = "product", ignore = true)
    CartItemDTO toDTO(CartItem source, @MappingTarget CartItemDTO target);
    CartItem toModel(CartItemDTO source, @MappingTarget CartItem target);
    @AfterMapping
    default void afterToDTO(CartItem source, @MappingTarget CartItemDTO target) {
        target.setProduct(ProductMapper.INSTANCE.convert(source.getAddedProduct()));
    }
    public default List<CartItemDTO> convertAll(List<CartItem> items){
        return items.stream().map(cartItem -> toDTO(cartItem, new CartItemDTO())).collect(Collectors.toList());
    }

    public default CartItemDTO convert(CartItem source){
        return toDTO(source, new CartItemDTO());
    }
}