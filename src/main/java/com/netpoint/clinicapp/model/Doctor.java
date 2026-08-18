package com.netpoint.clinicapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="doctors")
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="specialization_id")
    private Specialization specialization;
}
