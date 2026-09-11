package com.netpoint.clinicapp.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestDTO {

    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
    private Long doctorId;
    private int patientId;
}
