package com.epharmacy.app.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super(String.format("Could not find any order under this id : %s", orderId));
    }
}
