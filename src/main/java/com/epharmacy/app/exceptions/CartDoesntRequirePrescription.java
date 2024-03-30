package com.epharmacy.app.exceptions;

public class CartDoesntRequirePrescription extends RuntimeException{
    public CartDoesntRequirePrescription(Long cartId) {
        super(String.format("The cart : %s doesn't require prescription", cartId));
    }
}
