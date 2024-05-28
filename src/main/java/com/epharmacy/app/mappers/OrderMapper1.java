package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.customer.CustomerDTO;
import com.epharmacy.app.dto.deliveryman.DeliveryManDTO;
import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.order.OrderDTO1;
import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.DeliveryMan;
import com.epharmacy.app.model.Order;

public class OrderMapper1 {
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

    public static DeliveryManDTO toDeliveryManDTO(DeliveryMan deliveryMan) {
        if (deliveryMan == null) {
            return null;
        }
        DeliveryManDTO dto = new DeliveryManDTO();
        dto.setId(deliveryMan.getId());
        dto.setUsername(deliveryMan.getUsername());
        dto.setEmail(deliveryMan.getEmail());
        dto.setPhone(deliveryMan.getPhone());
        return dto;
    }

    public static OrderDTO1 toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO1 dto = new OrderDTO1();
        dto.setId(order.getId());
        dto.setCustomer(toCustomerDTO(order.getCustomer()));
        dto.setDeliveryMan(toDeliveryManDTO(order.getDeliveryMan()));
        return dto;
    }
}
