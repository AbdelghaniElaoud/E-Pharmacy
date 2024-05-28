package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.deliveryman.DeliveryManDTO;
import com.epharmacy.app.dto.order.OrderDTO1;
import com.epharmacy.app.dto.order.OrderDTO2;
import com.epharmacy.app.dto.pharmacist.PharmacistDTO;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.DeliveryMan;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.model.Pharmacist;

import static com.epharmacy.app.mappers.OrderMapper1.toDeliveryManDTO;

public class OrderMapper2 {
        public static CustomerDTO toCustomerDTO(Customer customer) {
            if (customer == null) {
                return null;
            }
            CustomerDTO dto = new CustomerDTO();
            dto.setId(customer.getId());
            dto.setUsername(customer.getUsername());
            dto.setEmail(customer.getEmail());
            dto.setPhone(customer.getPhone());
            dto.setAddress(customer.getAddress());
            return dto;
        }

        public static PharmacistDTO toPharmacistDTO(Pharmacist pharmacist) {
            if (pharmacist == null) {
                return null;
            }
            PharmacistDTO dto = new PharmacistDTO();
            dto.setId(pharmacist.getId());
            dto.setUsername(pharmacist.getUsername());
            dto.setEmail(pharmacist.getEmail());
            dto.setPhone(pharmacist.getPhone());
            return dto;
        }

        public static OrderDTO2 toOrderDTO(Order order) {
            if (order == null) {
                return null;
            }
            OrderDTO2 dto = new OrderDTO2();
            dto.setId(order.getId());
            dto.setCustomer(toCustomerDTO(order.getCustomer()));
            dto.setPharmacistDTO(toPharmacistDTO(order.getPharmacist()));
            dto.setOrderDTO(OrderMapper.INSTANCE.toDTO(order));
            return dto;
        }

}
