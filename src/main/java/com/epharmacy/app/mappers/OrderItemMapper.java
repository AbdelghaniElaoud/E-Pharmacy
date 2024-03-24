package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.orderitem.OrderItemDTO;
import com.epharmacy.app.model.OrderItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);


    @Mapping(source = "orderedProduct", target = "product", ignore = true)
    OrderItemDTO toDTO(OrderItem source);
    @Mapping(target = "orderedProduct", source = "product", ignore = true)
    OrderItem toModel(OrderItemDTO source);

    @AfterMapping
    default void afterToDTO(OrderItem source, @MappingTarget OrderItemDTO target) {
        target.setProduct(ProductMapper.INSTANCE.convert(source.getOrderedProduct()));
    }
    public default List<OrderItemDTO> convertAll(List<OrderItem> items){
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }
}