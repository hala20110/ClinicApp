package com.netpoint.clinicapp.Exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String message)
    {
        super(message);
    }
}
