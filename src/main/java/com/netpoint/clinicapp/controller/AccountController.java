package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acc")
public class AccountController {

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody RegisterDTO registerDTO)
    {
        return ResponseEntity.ok("Register Successfully");
    }
}
