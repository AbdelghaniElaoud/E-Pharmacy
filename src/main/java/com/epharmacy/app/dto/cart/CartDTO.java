package com.epharmacy.app.dto.cart;

import com.epharmacy.app.dto.cartitem.CartItemDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class CartDTO {
    private Long id;
    private String code;
    private Double totalPrice;
    private String address;
    private Set<PrescriptionDTO> prescriptions;
    private List<CartItemDTO> entries;
    private Long customerId;
}
