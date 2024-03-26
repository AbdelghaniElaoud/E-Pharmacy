package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.customer.CustomerRequestDTO;
import com.epharmacy.app.dto.review.ReviewDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer source);
    Customer toModel(CustomerRequestDTO source);


}
