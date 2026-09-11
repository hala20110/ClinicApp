package com.netpoint.clinicapp.model;

import com.netpoint.clinicapp.Enum.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="appointment_date",nullable = false)
    private LocalDate appointmentDate;
    @Column(name = "appointment_time",nullable = false)
    private LocalDateTime appointmentTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
    @Column(name="created_at")
    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn (name="doctor_id",nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn (name="patient_id",nullable = false)
    private Patient patient;


}
