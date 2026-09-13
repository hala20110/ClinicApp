package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.model.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordResponseDTO {
    private Long id;

    private Long appointmentId;

    private String diagnosis;
    private String patientName;
    private String doctorName;

    private String prescription;

    private LocalDate createdAt;
}
