package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.orderitem.OrderItemDTO;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.model.OrderItem;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "entries", target = "entries", ignore = true)
    OrderDTO toDTO(Order source);

    @Mapping(source = "entries", target = "entries", ignore = true)
    Order toModel(OrderDTO source);

    @AfterMapping
    default void afterToDTO(Order source, @MappingTarget OrderDTO target) {
        List<OrderItemDTO> entries = CollectionUtils.emptyIfNull(source.getEntries()).stream()
                .map(OrderItemMapper.INSTANCE::toDTO)
                .toList();
        target.setEntries(entries);
    }

    @AfterMapping
    default void afterToModel(OrderDTO source, @MappingTarget Order target) {
        List<OrderItem> entries = CollectionUtils.emptyIfNull(source.getEntries()).stream()
                .map(OrderItemMapper.INSTANCE::toModel)
                .toList();
        target.setEntries(entries);
    }

    default List<OrderDTO> toDTOList(List<Order> sourceList) {
        return sourceList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    default List<Order> toModelList(List<OrderDTO> sourceList) {
        return sourceList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
