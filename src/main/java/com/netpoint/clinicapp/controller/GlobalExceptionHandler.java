package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.ErrorResponse;
import com.netpoint.clinicapp.Exceptions.AppointmentConflictException;
import com.netpoint.clinicapp.Exceptions.PatientNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> patientNotFound(PatientNotFoundException exception, HttpServletRequest request)
    {
        log.warn("Patient Exception: {} path:{}", exception.getMessage(), request.getRequestURI());
        ErrorResponse patientError=new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(patientError);
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<ErrorResponse> appointmentConflict(AppointmentConflictException exception, HttpServletRequest request)
    {
        log.warn("Appointment Exception: {} path:{}", exception.getMessage(), request.getRequestURI());
        ErrorResponse appointmentError=new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT.value(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(appointmentError);
    }
}
