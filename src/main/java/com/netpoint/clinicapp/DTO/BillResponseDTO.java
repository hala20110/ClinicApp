package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.Enum.PaymentMethod;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class BillResponseDTO {
    private Long id;

    private Long appointmentId;

    private BigDecimal totalAmount;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
