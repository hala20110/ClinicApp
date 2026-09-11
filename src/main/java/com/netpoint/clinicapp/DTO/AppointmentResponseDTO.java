package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.Enum.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class AppointmentResponseDTO {
    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
    private Long doctorId;
    private int patientId;
    private AppointmentStatus status;
    private LocalDate createdAt;
}
