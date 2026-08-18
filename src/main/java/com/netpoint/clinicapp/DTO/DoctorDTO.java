package com.netpoint.clinicapp.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class DoctorDTO {
    private long id;
    private String name;
    private BigDecimal consultationFee;
    private boolean isActive;
    private LocalDateTime createdAt;
    private String specializationName;
}
