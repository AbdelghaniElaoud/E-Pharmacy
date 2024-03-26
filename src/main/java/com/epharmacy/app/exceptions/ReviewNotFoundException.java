package com.epharmacy.app.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long id) {
        super(String.format("Can not find review by id %s", id));
    }
}
