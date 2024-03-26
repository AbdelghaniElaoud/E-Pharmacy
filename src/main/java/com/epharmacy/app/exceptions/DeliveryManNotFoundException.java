package com.epharmacy.app.exceptions;

public class DeliveryManNotFoundException extends RuntimeException{
    public DeliveryManNotFoundException(){super("Could not find any deliveryMan");}
    public DeliveryManNotFoundException(Long id){
        super(String.format("Could not find any deliveryMan : %s", id));
    }

}
