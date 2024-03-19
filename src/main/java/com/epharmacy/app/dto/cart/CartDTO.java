package com.epharmacy.app.dto.cart;

import com.epharmacy.app.dto.cartItem.CartItemDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private Set<CartItemDTO> cartItems;
    private Long customerId;
}
