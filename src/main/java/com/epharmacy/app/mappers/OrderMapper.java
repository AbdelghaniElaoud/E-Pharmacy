package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order source);

    Order toModel(OrderDTO source);

    default List<OrderDTO> toDTOList(List<Order> sourceList) {
        return sourceList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    default List<Order> toModelList(List<OrderDTO> sourceList) {
        return sourceList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
