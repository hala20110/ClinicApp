package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.LoginDTO;
import com.netpoint.clinicapp.DTO.LoginResponse;
import com.netpoint.clinicapp.DTO.RegisterDTO;
import com.netpoint.clinicapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acc")
public class AccountController {
    @Autowired
    private AccountService service;
    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody RegisterDTO registerDTO)
    {
        service.registerAccount(registerDTO);
        return ResponseEntity.ok("Registered Successfully");
    }
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(service.login(loginDTO));
    }

}
