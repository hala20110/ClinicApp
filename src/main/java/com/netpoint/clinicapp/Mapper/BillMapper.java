package com.netpoint.clinicapp.Mapper;

import com.netpoint.clinicapp.DTO.BillResponseDTO;
import com.netpoint.clinicapp.model.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(source="appointment.id",target="appointmentId")
    BillResponseDTO ToDto(Bill bill);

    List<BillResponseDTO> ToDtoList(List<Bill> bills);
}
