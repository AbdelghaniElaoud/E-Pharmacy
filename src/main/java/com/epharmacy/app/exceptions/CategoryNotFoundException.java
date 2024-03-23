package com.epharmacy.app.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Can not find category by id %s", id));
    }
}
