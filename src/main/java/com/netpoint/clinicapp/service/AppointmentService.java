package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.AppointmentRequestDTO;
import com.netpoint.clinicapp.DTO.AppointmentResponseDTO;
import com.netpoint.clinicapp.DTO.AppointmentStatusUpdateDTO;
import com.netpoint.clinicapp.DTO.AppointmentUpdateDTO;
import com.netpoint.clinicapp.Enum.AppointmentStatus;
import com.netpoint.clinicapp.Mapper.AppointmentMapper;
import com.netpoint.clinicapp.model.Appointment;
import com.netpoint.clinicapp.model.Doctor;
import com.netpoint.clinicapp.model.DoctorSchedule;
import com.netpoint.clinicapp.model.Patient;
import com.netpoint.clinicapp.repository.AppointmentRepo;
import com.netpoint.clinicapp.repository.DoctorRepo;
import com.netpoint.clinicapp.repository.DoctorScheduleRepo;
import com.netpoint.clinicapp.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepo repo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private DoctorScheduleRepo doctorScheduleRepo;

    public AppointmentResponseDTO saveAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        // check if slot already exists or not
        boolean isExists = repo.existsByDoctorIdAndAppointmentTimeAndAppointmentDate(
                appointmentRequestDTO.getDoctorId(),
                appointmentRequestDTO.getAppointmentTime(),
                appointmentRequestDTO.getAppointmentDate()
        );
        if(isExists) {
            throw new RuntimeException("This Appointment Already Exists");
        }
        Patient patient=patientRepo.findById(appointmentRequestDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));
        Doctor doctor=doctorRepo.findById(appointmentRequestDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor Not Found"));
//        Appointment appointment = new Appointment();
//        appointment.setAppointmentDate(appointmentRequestDTO.getAppointmentDate());
//        appointment.setAppointmentTime(appointmentRequestDTO.getAppointmentTime());
//        appointment.setPatient(patient);
//        appointment.setDoctor(doctor);
//        appointment.setCreatedAt(LocalDate.now());
//        appointment.setStatus(AppointmentStatus.PENDING);
        Appointment savedAppointment=repo.save(appointmentMapper.toEntity(appointmentRequestDTO));
//        AppointmentResponseDTO appointmentResponseDTO=new AppointmentResponseDTO();
//        appointmentResponseDTO.setAppointmentDate(savedAppointment.getAppointmentDate());
//        appointmentResponseDTO.setAppointmentTime(savedAppointment.getAppointmentTime());
//        appointmentResponseDTO.setDoctorId(savedAppointment.getDoctor().getId());
//        appointmentResponseDTO.setPatientId(savedAppointment.getPatient().getId());
//        appointmentResponseDTO.setStatus(savedAppointment.getStatus());
//        appointmentResponseDTO.setCreatedAt(savedAppointment.getCreatedAt());
        return appointmentMapper.toDTO(savedAppointment);

    }
    public List<AppointmentResponseDTO> getAllAppointmentsForPatient(int patientId) {
        List<Appointment> appointments=repo.findByPatientId(patientId).orElseThrow(()-> new RuntimeException("Patient Not Found"));
        return appointments.stream().map(appointment ->{
            AppointmentResponseDTO appointmentResponseDTO=new AppointmentResponseDTO();
            appointmentResponseDTO.setAppointmentDate(appointment.getAppointmentDate());
            appointmentResponseDTO.setAppointmentTime(appointment.getAppointmentTime());
            appointmentResponseDTO.setDoctorId(appointment.getDoctor().getId());
            appointmentResponseDTO.setPatientId(appointment.getPatient().getId());
            appointmentResponseDTO.setStatus(appointment.getStatus());
            appointmentResponseDTO.setCreatedAt(appointment.getCreatedAt());
            return appointmentResponseDTO;
        }).toList();
    }
    public List<AppointmentResponseDTO> getAllAppointmentsForDoctor(Long doctorId) {
        List<Appointment> appointments=repo.findByDoctorId(doctorId).orElseThrow(()-> new RuntimeException("Doctor not found"));
        return appointments.stream().map(appointment -> {
            AppointmentResponseDTO appointmentResponseDTO=new AppointmentResponseDTO();
            appointmentResponseDTO.setAppointmentDate(appointment.getAppointmentDate());
            appointmentResponseDTO.setAppointmentTime(appointment.getAppointmentTime());
            appointmentResponseDTO.setDoctorId(appointment.getDoctor().getId());
            appointmentResponseDTO.setPatientId(appointment.getPatient().getId());
            appointmentResponseDTO.setStatus(appointment.getStatus());
            appointmentResponseDTO.setCreatedAt(appointment.getCreatedAt());
            return appointmentResponseDTO;
        }).toList();
    }
    public List<LocalTime> getAvailableSlots(Long doctorId,LocalDate date) {
        // extract day from dates
        DayOfWeek dayOfWeek=date.getDayOfWeek();
        // get schedule of dr
        DoctorSchedule schedule=doctorScheduleRepo.findByDoctorIdAndDayOfWeek(doctorId,dayOfWeek).orElseThrow(() -> new RuntimeException("Doctor has no schedule for " + dayOfWeek));
        //get slots
        List<LocalTime> allSlots=new ArrayList<>();
        LocalTime current=schedule.getStartTime();
        LocalTime end=schedule.getEndTime();
        while(current.isBefore(end)) {
            allSlots.add(current);
            current=current.plusMinutes(30);
        }
        // see which are booked
        List<Appointment> bookedAppointments=repo.findByDoctorIdAndAppointmentDate(doctorId,date);
        List<LocalTime> bookedSlots= bookedAppointments.stream().map(appointment -> appointment.getAppointmentTime().toLocalTime()).toList();
        // filter to get empty slots(get intersection between booked and all slots)
        return allSlots.stream().filter(slot->!bookedSlots.contains(slot)).toList();

    }

    public AppointmentResponseDTO updateAppointment(Long id, AppointmentUpdateDTO updateDTO){
        Appointment existingAppointment=repo.findById(id).orElseThrow(() -> new RuntimeException("Appointment Not Found"));
        LocalDate targetDate= updateDTO.getAppointmentDate()!=null
                ? updateDTO.getAppointmentDate()
                : existingAppointment.getAppointmentDate();
        LocalDateTime targetTime=updateDTO.getAppointmentTime()!=null
                ? updateDTO.getAppointmentTime()
                : existingAppointment.getAppointmentTime();

        boolean ifTimeOrDateChanged=!targetDate.equals(existingAppointment.getAppointmentDate())
                || !targetTime.equals(existingAppointment.getAppointmentTime());
        if(ifTimeOrDateChanged) {
            boolean isSlotTaken=repo.existsByDoctorIdAndAppointmentTimeAndAppointmentDate(existingAppointment.getDoctor().getId(),targetTime,targetDate);
            if(isSlotTaken) {
                throw new RuntimeException("Slot is already taken");
            }
        }
        appointmentMapper.updateEntityFromDto(updateDTO,existingAppointment);
        Appointment savedAppointment=repo.save(existingAppointment);
        return appointmentMapper.toDTO(savedAppointment);
    }

    public void deleteAppointment(Long id){
        if(!repo.existsById(id)) {
            throw new RuntimeException("Appointment Not Found");
        }
        repo.deleteById(id);
    }

    public AppointmentResponseDTO updateAppointmentStatus(Long id, AppointmentStatusUpdateDTO statusUpdateDTO){
        Appointment existingAppointment=repo.findById(id).orElseThrow(() -> new RuntimeException("Appointment Not Found"));
        if(statusUpdateDTO.getStatus()==null) {
            throw new RuntimeException("Status cannot be null");
        }
        existingAppointment.setStatus(statusUpdateDTO.getStatus());
        Appointment savedAppointment=repo.save(existingAppointment);
        return appointmentMapper.toDTO(savedAppointment);
    }

    public List<AppointmentResponseDTO> getUpcomingAppointments(){
        LocalDate today=LocalDate.now();
        List<Appointment> upcomingAppointments=repo.findByAppointmentDateGreaterThanEqualOrderByAppointmentDateAscAppointmentTimeAsc(today);
        return upcomingAppointments.stream().map(appointmentMapper::toDTO).toList();
    }

}
