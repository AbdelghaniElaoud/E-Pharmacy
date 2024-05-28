package com.epharmacy.app.dto.order;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.deliveryman.DeliveryManDTO;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.DeliveryMan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO1 {
    private Long id;
    private CustomerDTO customer;
    private DeliveryManDTO deliveryMan;

}
