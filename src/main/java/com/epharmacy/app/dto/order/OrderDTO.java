package com.epharmacy.app.dto.order;

import com.epharmacy.app.enums.OrderStatus;
import com.epharmacy.app.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {
    private Long id;
    private Float totalPrice;
    private String address;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    /*private Long customerId;
    private Set<Long> prescriptionIds;
    private Set<Long> orderItemIds;
    private List<Long> reviewIds;*/
}
