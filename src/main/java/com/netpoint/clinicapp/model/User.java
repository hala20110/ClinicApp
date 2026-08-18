package com.netpoint.clinicapp.model;

import com.netpoint.clinicapp.Enum.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter @Setter

public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
