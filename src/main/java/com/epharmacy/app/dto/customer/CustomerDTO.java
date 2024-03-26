package com.epharmacy.app.dto.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class CustomerDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    /*private Set<Long> prescriptionIds;
    private List<Long> cartIds;
    private Set<Long> orderIds;
    private List<Long> reviewIds;*/

}
