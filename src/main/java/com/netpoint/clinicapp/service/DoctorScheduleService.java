package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.DoctorScheduleRequestDTO;
import com.netpoint.clinicapp.DTO.DoctorScheduleResponseDTO;
import com.netpoint.clinicapp.model.Doctor;
import com.netpoint.clinicapp.model.DoctorSchedule;
import com.netpoint.clinicapp.repository.DoctorRepo;
import com.netpoint.clinicapp.repository.DoctorScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DoctorScheduleService {
    @Autowired
    private DoctorScheduleRepo doctorScheduleRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    public DoctorScheduleResponseDTO addDoctorSchedule(DoctorScheduleRequestDTO requestDTO) {
        Doctor doctor=doctorRepo.findById(requestDTO.getDoctorId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found with ID: "+requestDTO.getDoctorId()));
        DoctorSchedule schedule=new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(requestDTO.getDayOfWeek());
        schedule.setStartTime(requestDTO.getStartTime());
        schedule.setEndTime(requestDTO.getEndTime());

        DoctorSchedule savedSchedule=doctorScheduleRepo.save(schedule);

        DoctorScheduleResponseDTO responseDTO=new DoctorScheduleResponseDTO();
        responseDTO.setDoctorId(savedSchedule.getDoctor().getId());
        responseDTO.setDoctorName(savedSchedule.getDoctor().getName());
        responseDTO.setDayOfWeek(savedSchedule.getDayOfWeek());
        responseDTO.setStartTime(savedSchedule.getStartTime());
        responseDTO.setEndTime(savedSchedule.getEndTime());
        responseDTO.setId(savedSchedule.getId());
        return responseDTO;


    }
    public List<DoctorScheduleResponseDTO> getAllSchedules(){
        List<DoctorSchedule> schedules=doctorScheduleRepo.findAll();
        return schedules.stream().map(schedule->{
            DoctorScheduleResponseDTO dto=new DoctorScheduleResponseDTO();
            dto.setId(schedule.getId());
            dto.setDoctorName(schedule.getDoctor().getName());
            dto.setDoctorId(schedule.getDoctor().getId());
            dto.setDayOfWeek(schedule.getDayOfWeek());
            dto.setStartTime(schedule.getStartTime());
            dto.setEndTime(schedule.getEndTime());
            return dto;
        }).toList();
    }

    public DoctorScheduleResponseDTO getDoctorScheduleById(Long id){
        DoctorSchedule schedule=doctorScheduleRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found with ID: "+id));
        DoctorScheduleResponseDTO dto=new DoctorScheduleResponseDTO();
        dto.setId(schedule.getId());
        dto.setDoctorName(schedule.getDoctor().getName());
        dto.setDoctorId(schedule.getDoctor().getId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        return dto;

    }
    public DoctorScheduleResponseDTO updateSchedule(Long id, DoctorScheduleRequestDTO requestDTO) {
        DoctorSchedule schedule = doctorScheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with ID: " + id));

        Doctor doctor = doctorRepo.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found with ID: " + requestDTO.getDoctorId()));

        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(requestDTO.getDayOfWeek());
        schedule.setStartTime(requestDTO.getStartTime());
        schedule.setEndTime(requestDTO.getEndTime());

        DoctorSchedule updatedSchedule = doctorScheduleRepo.save(schedule);

        DoctorScheduleResponseDTO responseDTO = new DoctorScheduleResponseDTO();
        responseDTO.setId(updatedSchedule.getId());
        responseDTO.setDoctorId(updatedSchedule.getDoctor().getId());
        responseDTO.setDoctorName(updatedSchedule.getDoctor().getName());
        responseDTO.setDayOfWeek(updatedSchedule.getDayOfWeek());
        responseDTO.setStartTime(updatedSchedule.getStartTime());
        responseDTO.setEndTime(updatedSchedule.getEndTime());

        return responseDTO;
    }
}
