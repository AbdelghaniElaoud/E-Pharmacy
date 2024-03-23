package com.epharmacy.app.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("Can not find product by id %s", id));
    }
}
