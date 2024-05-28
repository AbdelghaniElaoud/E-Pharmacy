package com.epharmacy.app.dto.order;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.pharmacist.PharmacistDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO2 {
    private Long id;
    private CustomerDTO customer;
    private PharmacistDTO pharmacistDTO;
    private OrderDTO orderDTO;
}
