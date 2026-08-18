package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.CreateSpecializationDTO;
import com.netpoint.clinicapp.DTO.SpecializationResponseDTO;
import com.netpoint.clinicapp.model.Specialization;
import com.netpoint.clinicapp.repository.SpecializationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {
    @Autowired
    private SpecializationRepo specializationRepo;
    public SpecializationResponseDTO CreateSpecialization(CreateSpecializationDTO createSpecializationDTO) {
        Specialization spec = new Specialization();
        spec.setName(createSpecializationDTO.getName());
        spec.setDescription(createSpecializationDTO.getDescription());
        Specialization savedspec=specializationRepo.save(spec);
        SpecializationResponseDTO specializationResponseDTO=new SpecializationResponseDTO();
        specializationResponseDTO.setId(savedspec.getId());
        specializationResponseDTO.setName(savedspec.getName());
        specializationResponseDTO.setDescription(savedspec.getDescription());
        return specializationResponseDTO;
    }
    public List<SpecializationResponseDTO> findallSpecs() {
        return specializationRepo.findAll().stream()
                .map(specialization ->
                {
                    SpecializationResponseDTO response=new SpecializationResponseDTO();
                    response.setId(specialization.getId());
                    response.setName(specialization.getName());
                    response.setDescription(specialization.getDescription());
                    return response;
                }
                ).toList();
    }

}
