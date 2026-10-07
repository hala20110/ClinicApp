package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.LoginDTO;
import com.netpoint.clinicapp.DTO.LoginResponse;
import com.netpoint.clinicapp.DTO.RegisterDTO;
import com.netpoint.clinicapp.model.User;
import com.netpoint.clinicapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    UserRepo userRepo;
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

        userRepo.save(user);
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
