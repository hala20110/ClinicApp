package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.BillAnalyticsSummary;
import com.netpoint.clinicapp.DTO.BillResponseDTO;
import com.netpoint.clinicapp.DTO.PayRequestDTO;
import com.netpoint.clinicapp.Enum.AppointmentStatus;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import com.netpoint.clinicapp.Mapper.BillMapper;
import com.netpoint.clinicapp.model.Appointment;
import com.netpoint.clinicapp.model.Bill;
import com.netpoint.clinicapp.repository.AppointmentRepo;
import com.netpoint.clinicapp.repository.BillRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {
    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    BillRepo billRepo;
    @Autowired
    BillMapper billMapper;
    @Transactional
    public BillResponseDTO generateBill(Long appointmentId){
        Appointment appointment= appointmentRepo.findById(appointmentId).orElseThrow(()->new RuntimeException("appointment not found"));
        billRepo.findByAppointmentId(appointmentId).ifPresent(b->{
            throw new RuntimeException("bill already exists");
        });


        if(appointment.getStatus()==AppointmentStatus.CANCELLED){
            throw new RuntimeException("appointment already cancelled");
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);

        BigDecimal total=appointment.getDoctor().getConsultationFee();
        Bill bill=new Bill();
        bill.setAppointment(appointment);
        bill.setPaymentStatus(PaymentStatus.PENDING);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setTotalAmount(total);
        Bill savedBill=billRepo.save(bill);

        return billMapper.ToDto(savedBill);
    }

    public BillResponseDTO payBill(PayRequestDTO payRequestDTO, Long BillId)
    {
        Bill bill=billRepo.findById(BillId).orElseThrow(()->new RuntimeException("bill not found"));

        if(bill.getPaymentStatus()==PaymentStatus.PAID)
        {
            throw new RuntimeException("bill already paid");
        }
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setPaymentMethod(payRequestDTO.getPaymentMethod());
        Bill savedBill = billRepo.save(bill);
        return billMapper.ToDto(savedBill);
    }

    public List<BillResponseDTO> findBillsByPatientId(Long patientId)
    {
        List<Bill> bills= billRepo.findByAppointmentPatientId(patientId);
        return billMapper.ToDtoList(bills);
    }

    public BillResponseDTO findByAppointmentId(Long appointmentId)
    {
        Bill bill =billRepo.findByAppointmentId(appointmentId).orElseThrow(()->new RuntimeException("bill not found"));
        return billMapper.ToDto(bill);
    }

    public List<BillResponseDTO> findByPaymentStatus(PaymentStatus paymentStatus)
    {
        List<Bill> bills= billRepo.findByPaymentStatus(paymentStatus);
        return billMapper.ToDtoList(bills);
    }

    public BillAnalyticsSummary findPaymentSummary()
    {
        return billRepo.getAnalyticsSummary();
    }

}
