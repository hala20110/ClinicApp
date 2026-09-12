package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.BillAnalyticsSummary;
import com.netpoint.clinicapp.DTO.BillResponseDTO;
import com.netpoint.clinicapp.DTO.PayRequestDTO;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import com.netpoint.clinicapp.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/appointment/{id}")
    public ResponseEntity<BillResponseDTO> generateBill(@PathVariable Long id){
        return  ResponseEntity.ok(billService.generateBill(id));
    }

    @PutMapping("pay/{id}")
    public ResponseEntity<BillResponseDTO> payBill(@PathVariable Long id, @RequestBody PayRequestDTO payRequestDTO)
    {
        return ResponseEntity.ok(billService.payBill(payRequestDTO,id));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<BillResponseDTO>> findBillByPatientId(@PathVariable Long id)
    {
        return ResponseEntity.ok(billService.findBillsByPatientId(id));
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<BillResponseDTO> findByAppointmentId(@PathVariable Long id)
    {
        return ResponseEntity.ok(billService.findByAppointmentId(id));
    }

    @GetMapping("/payment-status")
    public ResponseEntity<List<BillResponseDTO>> findAllBillsByPaymentStatus(@RequestParam PaymentStatus status)
    {
        return ResponseEntity.ok(billService.findByPaymentStatus(status));
    }

    @GetMapping("/bills-summary")
    public ResponseEntity<BillAnalyticsSummary> getAnalyticsSummary()
    {
        return ResponseEntity.ok(billService.findPaymentSummary());
    }
}
