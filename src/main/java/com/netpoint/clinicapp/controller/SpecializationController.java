package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.CreateSpecializationDTO;
import com.netpoint.clinicapp.DTO.SpecializationResponseDTO;
import com.netpoint.clinicapp.model.Specialization;
import com.netpoint.clinicapp.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecializationController {
    @Autowired
    SpecializationService specializationService;
    @PostMapping
    public ResponseEntity<SpecializationResponseDTO> addSpecialization(@RequestBody CreateSpecializationDTO createSpecializationDTO) {
        SpecializationResponseDTO response= specializationService.CreateSpecialization(createSpecializationDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<SpecializationResponseDTO>> getAllSpecializations() {
        List<SpecializationResponseDTO> response=specializationService.findallSpecs();
        return ResponseEntity.ok(response);
    }
}
