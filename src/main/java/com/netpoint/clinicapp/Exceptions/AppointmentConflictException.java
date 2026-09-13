package com.netpoint.clinicapp.Exceptions;

public class AppointmentConflictException extends RuntimeException
{
    public AppointmentConflictException(String message)
    {
        super(message);
    }
}
