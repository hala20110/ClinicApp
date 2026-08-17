package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.model.Patient;
import com.netpoint.clinicapp.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;

    public Patient savePatient(Patient patient){
        return patientRepo.save(patient);
    }

    public  Patient findPatientById(int id){
        return patientRepo.findById(id).orElse(null);
    }

    public List<Patient> findAllPatients(){
        return patientRepo.findAll();
    }

    public List<String> findPatientsNameByAgeGreaterThan(int age){
        return patientRepo.findPatientNamesByAgeGreaterThan(age);
    }
    public void deletePatient(int id){
        patientRepo.deleteById(id);
    }
    /*public Patient updatePatient(int id,Patient patient){
        return patientRepo.updatePatient(id,patient);
    }*/

}
