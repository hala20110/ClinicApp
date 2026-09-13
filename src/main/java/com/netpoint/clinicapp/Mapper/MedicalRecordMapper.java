package com.netpoint.clinicapp.Mapper;

import com.netpoint.clinicapp.DTO.MedicalRecordResponseDTO;
import com.netpoint.clinicapp.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source="appointment.id",target="appointmentId")
    @Mapping(source="appointment.patient.name",target="patientName")
    @Mapping(source="appointment.doctor.name",target="doctorName")
    MedicalRecordResponseDTO toMedicalRecordResponse(MedicalRecord medicalRecord);

    List<MedicalRecordResponseDTO> toMedicalRecordResponseList(List<MedicalRecord> medicalRecords);
}
