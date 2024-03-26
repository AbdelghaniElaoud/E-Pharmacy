package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.review.ResponseReviewDTO;
import com.epharmacy.app.dto.review.ReviewDTO;
import com.epharmacy.app.dto.review.ReviewRequestDTO;
import com.epharmacy.app.exceptions.CustomerNotFoundException;
import com.epharmacy.app.exceptions.DeliveryManNotFoundException;
import com.epharmacy.app.exceptions.OrderNotFoundException;
import com.epharmacy.app.model.*;
import com.epharmacy.app.service.CustomerService;
import com.epharmacy.app.service.DeliveryManService;
import com.epharmacy.app.service.OrderService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewDTO toDTO(Review source);

    @Mapping(target = "customer", ignore = true)
    ResponseReviewDTO toDTOResponse(Review source);

    Review toModel(ReviewRequestDTO source, @Context CustomerService customerService, @Context DeliveryManService deliveryManService, @Context OrderService orderService);


    @AfterMapping
    default void afterToDTO(Review review, @MappingTarget ReviewDTO reviewDTO) {
        reviewDTO.setCustomer(CustomerMapper.INSTANCE.toDTO(review.getCustomer()));
        reviewDTO.setDeliveryMan(DeliveryManMapper.INSTANCE.toDTO(review.getDeliveryMan()));
        reviewDTO.setOrder(OrderMapper.INSTANCE.toDTO(review.getOrder()));
    }

    @AfterMapping
    default void afterToDTOResponse(Review review, @MappingTarget ResponseReviewDTO reviewDTO) {
        reviewDTO.setCustomer(review.getCustomer().getFullName());
    }

    @AfterMapping
    default void afterToModel(ReviewRequestDTO reviewRequestDTO, @MappingTarget Review review, @Context CustomerService customerService, @Context DeliveryManService deliveryManService, @Context OrderService orderService) {
        Long customerId = reviewRequestDTO.getCustomerId();
        Long deliveryManId = reviewRequestDTO.getDeliveryManId();
        Long orderId = reviewRequestDTO.getOrderId();
        Optional<Customer> customerOptional = customerService.findById(customerId);
        Optional<DeliveryMan> deliveryManOptional = deliveryManService.findById(deliveryManId);
        Optional<Order> orderOptional = orderService.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(customerId);
        }
        if (deliveryManOptional.isEmpty()) {
            throw new DeliveryManNotFoundException(deliveryManId);
        }
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        review.setCustomer(customerOptional.get());
        review.setDeliveryMan(deliveryManOptional.get());
        review.setOrder(orderOptional.get());
        review.setStatus(reviewRequestDTO.getStatus());
    }
    default List<ResponseReviewDTO> toDTOResponseList(List<Review> items){
        return items.stream().map(this::toDTOResponse).toList();
    }
}
