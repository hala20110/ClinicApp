package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.RegisterDTO;
import com.netpoint.clinicapp.model.User;
import com.netpoint.clinicapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    UserRepo userRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();


    public void registerAccount(RegisterDTO dto){
        User user= new User();
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        userRepo.save(user);
    }
}
