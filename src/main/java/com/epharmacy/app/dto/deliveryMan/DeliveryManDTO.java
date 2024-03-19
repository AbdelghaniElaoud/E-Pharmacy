package com.epharmacy.app.dto.deliveryMan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.List;

@Getter
@Setter
@ToString
public class DeliveryManDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Set<Long> orderIds;
    private List<Long> reviewIds;

}
