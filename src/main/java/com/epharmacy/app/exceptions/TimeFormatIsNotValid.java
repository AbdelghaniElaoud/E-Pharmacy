package com.epharmacy.app.exceptions;

public class TimeFormatIsNotValid extends RuntimeException{
    public TimeFormatIsNotValid() {super("The date should be formatted like this : dd/MM/yyyy");
    }
}
