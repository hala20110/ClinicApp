package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.DoctorDTO;
import com.netpoint.clinicapp.model.Doctor;
import com.netpoint.clinicapp.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepo doctorRepo;

    public List<DoctorDTO> findAllDoctorsActiveAndspecId(Boolean isActive, Long specId) {
        List<Doctor> doctors= new ArrayList<>();
     if(isActive!=null && specId!=null){
         doctors = doctorRepo.findAllDocsActiveAndspecId(isActive,specId);
     }
     else{
         doctors=doctorRepo.findAll();
     }
     return  doctors.stream().map(doctor -> {
         DoctorDTO doctorDTO = new DoctorDTO();
         doctorDTO.setId(doctor.getId());
         doctorDTO.setName(doctor.getName());
         doctorDTO.setActive(doctor.isActive());
         doctorDTO.setConsultationFee(doctor.getConsultationFee());
         doctorDTO.setCreatedAt(doctor.getCreatedAt());
         doctorDTO.setSpecializationName(doctor.getSpecialization().getName());
         return doctorDTO;
     }).toList();
    }

    public DoctorDTO findDoctorById(Long id) {
        Doctor doc=doctorRepo.findById(id).get();
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doc.getId());
        doctorDTO.setName(doc.getName());
        doctorDTO.setActive(doc.isActive());
        doctorDTO.setConsultationFee(doc.getConsultationFee());
        doctorDTO.setCreatedAt(doc.getCreatedAt());
        doctorDTO.setSpecializationName(doc.getSpecialization().getName());
        return doctorDTO;
        }
}
