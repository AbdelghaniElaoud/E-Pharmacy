package com.epharmacy.app.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super(String.format("Could not find customer by id %s", id));
    }
}
