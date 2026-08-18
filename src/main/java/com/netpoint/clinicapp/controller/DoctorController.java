package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.DoctorDTO;
import com.netpoint.clinicapp.model.Doctor;
import com.netpoint.clinicapp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors(@RequestParam(required = false) Boolean active, @RequestParam(required = false) Long specId) {
        List<DoctorDTO> doctors=doctorService.findAllDoctorsActiveAndspecId(active,specId);
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }
}
