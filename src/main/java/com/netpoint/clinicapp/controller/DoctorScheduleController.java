package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.DoctorScheduleRequestDTO;
import com.netpoint.clinicapp.DTO.DoctorScheduleResponseDTO;
import com.netpoint.clinicapp.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class DoctorScheduleController {
    @Autowired
    private DoctorScheduleService doctorScheduleService;
    @PostMapping
    public ResponseEntity<DoctorScheduleResponseDTO> addDoctorSchedule(@RequestBody DoctorScheduleRequestDTO doctorScheduleRequestDTO) {
        DoctorScheduleResponseDTO createdSchedule=doctorScheduleService.addDoctorSchedule(doctorScheduleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }
    @GetMapping
    public ResponseEntity<List<DoctorScheduleResponseDTO>> getAllSchedules() {
        List<DoctorScheduleResponseDTO> schedules=doctorScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        DoctorScheduleResponseDTO schedule = doctorScheduleService.getDoctorScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorScheduleResponseDTO> updateSchedule(
            @PathVariable Long id,
            @RequestBody DoctorScheduleRequestDTO requestDTO) {
        DoctorScheduleResponseDTO updatedSchedule = doctorScheduleService.updateSchedule(id, requestDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

}
