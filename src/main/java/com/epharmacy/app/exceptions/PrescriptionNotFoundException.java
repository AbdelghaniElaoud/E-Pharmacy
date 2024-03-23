package com.epharmacy.app.exceptions;

public class PrescriptionNotFoundException extends RuntimeException{
    public PrescriptionNotFoundException(Long id){super(String.format("Could not find any prescription in this cart  %s", id));}
}
