package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.LoginDTO;
import com.netpoint.clinicapp.DTO.LoginResponse;
import com.netpoint.clinicapp.DTO.RegisterDTO;
import com.netpoint.clinicapp.Enum.Role;
import com.netpoint.clinicapp.model.Doctor;
import com.netpoint.clinicapp.model.Patient;
import com.netpoint.clinicapp.model.User;
import com.netpoint.clinicapp.repository.DoctorRepo;
import com.netpoint.clinicapp.repository.PatientRepo;
import com.netpoint.clinicapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JWTService jwtService;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);


    public void registerAccount(RegisterDTO dto){
        User user= new User();
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User savedUser = userRepo.save(user);

        if (dto.getRole() == Role.PATIENT) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setName(extractDefaultName(dto.getEmail()));
            patient.setActive(true);
            patient.setCreatedAt(LocalDateTime.now());
            patientRepo.save(patient);

        } else if (dto.getRole() == Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setName("Dr. " + extractDefaultName(dto.getEmail()));
            doctor.setActive(true);
            doctor.setCreatedAt(LocalDateTime.now());
            doctorRepo.save(doctor);
        }
    }
    private String extractDefaultName(String email) {
        if (email == null || !email.contains("@")) return "New User";
        String namePart = email.split("@")[0];
        return namePart.substring(0, 1).toUpperCase() + namePart.substring(1);
    }
    public LoginResponse login(LoginDTO dto){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        User user=userRepo.findByEmail(dto.getEmail()).orElse(null);
        String accessToken = jwtService.generateToken(user.getEmail(),user.getRole().toString());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccesstoken(accessToken);
        loginResponse.setMessage("Login Success");
        return loginResponse;
    }
}
