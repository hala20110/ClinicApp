package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.MedicalRecordResponseDTO;
import com.netpoint.clinicapp.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<MedicalRecordResponseDTO>> findAllMedicalRecordsByPatient(@PathVariable Integer id)
    {
        return ResponseEntity.ok(medicalRecordService.findByAppointmentPatientId(id));
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<MedicalRecordResponseDTO>> findAllMedicalRecordsByDoctor(@PathVariable Long id)
    {
        return ResponseEntity.ok(medicalRecordService.findByAppointmentDoctorId(id));
    }

    @GetMapping("/prescription/patient/{id}")
    public ResponseEntity<List<String>> findAllPrescriptionsByPatientId(@PathVariable Integer id)
    {
        return ResponseEntity.ok(medicalRecordService.getPrescriptionByPatientId(id));
    }

}
