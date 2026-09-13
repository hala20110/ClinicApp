package com.netpoint.clinicapp.repository;

import com.netpoint.clinicapp.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByAppointmentPatientId(Integer patientId);
    List<MedicalRecord> findByAppointmentDoctorId(Long doctorId);
}
