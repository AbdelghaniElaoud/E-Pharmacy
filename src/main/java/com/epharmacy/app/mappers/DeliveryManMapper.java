package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.deliveryman.DeliveryManDTO;
import com.epharmacy.app.dto.review.ReviewDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.model.DeliveryMan;
import com.epharmacy.app.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DeliveryManMapper {

    DeliveryManMapper INSTANCE = Mappers.getMapper(DeliveryManMapper.class);

    DeliveryManDTO toDTO(DeliveryMan source);
    //Review toModel(ReviewRequestDTO source);
}
