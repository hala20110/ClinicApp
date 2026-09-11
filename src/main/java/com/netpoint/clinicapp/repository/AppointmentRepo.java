package com.netpoint.clinicapp.repository;

import com.netpoint.clinicapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentTimeAndAppointmentDate(Long doctorId, LocalDateTime appointmentTime, LocalDate appointmentDate);
    Optional<List<Appointment>> findByPatientId(int patientId);
    Optional<List<Appointment>> findByDoctorId(Long doctorId);
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

}
