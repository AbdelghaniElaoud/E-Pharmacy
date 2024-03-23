package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.cart.CartDTO;
import com.epharmacy.app.dto.cartitem.CartItemDTO;
import com.epharmacy.app.model.Cart;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);


    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "entries", target = "entries", ignore = true)
    CartDTO toDTO(Cart source, @MappingTarget CartDTO target);
    @Mapping(source = "customerId", target = "customer.id", ignore = true)
    Cart toModel(CartDTO source, @MappingTarget Cart target);
    @AfterMapping
    default void afterToDTO(Cart source, @MappingTarget CartDTO target) {
        List<CartItemDTO> entries = CollectionUtils.emptyIfNull(source.getEntries()).stream()
                .map(CartItemMapper.INSTANCE::convert)
                .toList();
        target.setEntries(entries);
    }
    public default List<CartDTO> convertAll(List<Cart> items){
        return items.stream().map(cart -> toDTO(cart, new CartDTO())).toList();
    }

    public default CartDTO convert(Cart source){
        return toDTO(source, new CartDTO());
    }
}