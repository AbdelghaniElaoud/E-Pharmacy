package com.epharmacy.app.exceptions;

public class CustomerAlreadyHasCart extends RuntimeException{

    public CustomerAlreadyHasCart(Long customerId) {
        super(String.format("The customer with the id {} has already an active cart",customerId));
    }
}
