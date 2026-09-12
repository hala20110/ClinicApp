package com.netpoint.clinicapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
public class AppointmentUpdateDTO {
    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
}
