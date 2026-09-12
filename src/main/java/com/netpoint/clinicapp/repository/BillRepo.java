package com.netpoint.clinicapp.repository;

import com.netpoint.clinicapp.DTO.BillAnalyticsSummary;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import com.netpoint.clinicapp.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill,Long> {

    Optional<Bill> findByAppointmentId(Long appointmentId);
    List<Bill> findByAppointmentPatientId(Long patientId);
    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);

    @Query("SELECT new com.netpoint.clinicapp.DTO.BillAnalyticsSummary(" +
        "COUNT(b), " +
        "SUM(CASE WHEN b.paymentStatus = 'PENDING' THEN 1 ELSE 0 END), " + //1--> pending 0-->paid
        "SUM(CASE WHEN b.paymentStatus = 'PAID' THEN 1 ELSE 0 END), " +
        "SUM(CASE WHEN b.paymentStatus = 'PAID' THEN b.totalAmount ELSE 0 END)) " +
        "FROM Bill b")
    BillAnalyticsSummary getAnalyticsSummary();
}
