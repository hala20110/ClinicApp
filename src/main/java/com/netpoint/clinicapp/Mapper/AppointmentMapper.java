package com.netpoint.clinicapp.Mapper;

import com.netpoint.clinicapp.DTO.AppointmentRequestDTO;
import com.netpoint.clinicapp.DTO.AppointmentResponseDTO;
import com.netpoint.clinicapp.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source="doctorId",target="doctor.id")
    @Mapping(source="patientId",target="patient.id")
    @Mapping(ignore = true,target="id")
//    @Mapping(ignore = true,target="status")
    @Mapping(ignore = true,target="createdAt")
    @Mapping(target = "status", expression = "java(com.netpoint.clinicapp.Enum.AppointmentStatus.PENDING)")
    public Appointment toEntity(AppointmentRequestDTO appointmentrequestdto);
    @Mapping(source="doctor.id",target="doctorId")
    @Mapping(source="patient.id",target="patientId")
    AppointmentResponseDTO toDTO(Appointment appointment);
}
