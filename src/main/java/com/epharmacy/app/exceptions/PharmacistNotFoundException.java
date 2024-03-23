package com.epharmacy.app.exceptions;

public class PharmacistNotFoundException extends RuntimeException{
    public PharmacistNotFoundException(){super("Could not find any pharmacist");}

}
