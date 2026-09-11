package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.AppointmentRequestDTO;
import com.netpoint.clinicapp.DTO.AppointmentResponseDTO;
import com.netpoint.clinicapp.model.Appointment;
import com.netpoint.clinicapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO appointment) {
        return ResponseEntity.ok(appointmentService.saveAppointment(appointment));
    }
    @GetMapping("/patient/{Id}")
    public ResponseEntity<List<AppointmentResponseDTO>> findByPatientId(@PathVariable Integer Id) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsForPatient(Id));
    }
    @GetMapping("/doctor/{Id}")
    public ResponseEntity<List<AppointmentResponseDTO>> findByDoctorId(@PathVariable Long Id) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsForDoctor(Id));
    }
    @GetMapping("/available-slots")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(@RequestParam Long doctorId, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime> availableSlots = appointmentService.getAvailableSlots(doctorId,date);
        return ResponseEntity.ok(availableSlots);
    }
}
