package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.model.Patient;
import com.netpoint.clinicapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public Patient savePatient(@RequestBody Patient patient){
        return patientService.savePatient(patient);
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id){
        Patient patient =patientService.findPatientById(id);
        if (patient == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public List<Patient> getPatients(){
        return patientService.findAllPatients();
    }

    @GetMapping("/getnames")
    public List<String> getNamesByAgeGreaterThan(@RequestParam int age){
        return patientService.findPatientsNameByAgeGreaterThan(age);
    }
    @DeleteMapping("{id}")
    public void deletePatient(@PathVariable int id){
        patientService.deletePatient(id);
        System.out.println("Delete patient successfully");
    }
    /*@PutMapping("{id}")
    public Patient updatePatient(@RequestBody Patient patient, @PathVariable int id){
        return patientService.updatePatient(id,patient);
    }*/

    @GetMapping("/allActivePatients")
    public Map<String,Integer> getAllActivePatients(){
        return patientService.findActivePatientsCount();
    }
    @GetMapping("/allPatients")
    public Map<String,Long> getAllPatients(){
        return patientService.findAllPatientsCount();
    }
}
