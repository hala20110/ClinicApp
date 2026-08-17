package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.model.Patient;
import com.netpoint.clinicapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Patient getPatientById(@PathVariable int id){
        return patientService.findPatientById(id);
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
}
