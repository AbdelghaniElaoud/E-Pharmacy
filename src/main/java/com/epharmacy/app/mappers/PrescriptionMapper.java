package com.epharmacy.app.mappers;


import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.orderitem.OrderItemDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.model.Prescription;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PrescriptionMapper {
    PrescriptionMapper INSTANCE = Mappers.getMapper(PrescriptionMapper.class);



    PrescriptionDTO toDTO(Prescription source);

    /*@Mapping(source = "entries", target = "entries", ignore = true)
    Order toModel(OrderDTO source);*/

    @AfterMapping
    default void afterToDTO(Prescription source, @MappingTarget PrescriptionDTO target) {
        target.setLink(source.getMedia().getLink());
    }
}
