package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.Enum.Role;
import lombok.Data;

@Data
public class RegisterDTO {

    private String email;
    private String password;
    private Role role;
}
