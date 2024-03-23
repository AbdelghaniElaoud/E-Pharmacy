package com.epharmacy.app.exceptions;

public class DeliveryManNotFoundException extends RuntimeException{
    public DeliveryManNotFoundException(){super("Could not find any deliveryMan");}

}
