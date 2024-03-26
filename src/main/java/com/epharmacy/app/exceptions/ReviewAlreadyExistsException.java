package com.epharmacy.app.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException {
    public ReviewAlreadyExistsException(Long id) {
        super(String.format("Review for order already exists ", id));
    }
}
