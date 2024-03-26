package com.epharmacy.app.dto.review;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.deliveryman.DeliveryManDTO;
import com.epharmacy.app.dto.order.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private Integer id;
    private String label;
    private Integer rate;
    private CustomerDTO customer;
    private DeliveryManDTO deliveryMan;
    private OrderDTO order;
}
