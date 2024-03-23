package com.epharmacy.app.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super(String.format("Can not find cart by id %s", id));
    }
}
