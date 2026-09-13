package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.MedicalRecordResponseDTO;
import com.netpoint.clinicapp.Exceptions.PatientNotFoundException;
import com.netpoint.clinicapp.Mapper.MedicalRecordMapper;
import com.netpoint.clinicapp.model.MedicalRecord;
import com.netpoint.clinicapp.repository.DoctorRepo;
import com.netpoint.clinicapp.repository.MedicalRecordRepo;
import com.netpoint.clinicapp.repository.PatientRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepo medicalRecordRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;
    @Autowired
    private DoctorRepo doctorRepo;

    public List<MedicalRecordResponseDTO> findByAppointmentPatientId(Integer patientId)
    {
        log.info("patient id of medical record is {}",patientId);
        if(!patientRepo.existsById(patientId))
        {
            throw new PatientNotFoundException("patient not found");
        }
        List<MedicalRecord> records=medicalRecordRepo.findByAppointmentPatientId(patientId);
        return medicalRecordMapper.toMedicalRecordResponseList(records);

    }
    public List<MedicalRecordResponseDTO> findByAppointmentDoctorId(Long doctorId)
    {
        log.info("doctor id of medical record is {}",doctorId);
        if(!doctorRepo.existsById(doctorId))
        {
            throw new RuntimeException("Doctor not found");
        }
        List<MedicalRecord> records=medicalRecordRepo.findByAppointmentDoctorId(doctorId);
        return medicalRecordMapper.toMedicalRecordResponseList(records);

    }

    public List<String> getPrescriptionByPatientId(Integer patientId)
    {
        log.info("patient id of prescription is {}",patientId);
        if(!patientRepo.existsById(patientId))
        {
            throw new RuntimeException("Patient not found");
        }
        List<MedicalRecord> records=medicalRecordRepo.findByAppointmentPatientId(patientId);
        return records.stream().map(MedicalRecord::getPrescription).filter(prescription->prescription!=null).toList();

    }

}
