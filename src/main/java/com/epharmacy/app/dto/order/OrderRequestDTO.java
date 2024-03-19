package com.epharmacy.app.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private Float totalPrice;
    private String address;
    private String orderStatus;
    private String paymentStatus;
    private Long customerId;
    private Long deliveryManId;
    private Long pharmacistId;
}
