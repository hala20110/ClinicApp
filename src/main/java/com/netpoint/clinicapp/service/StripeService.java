package com.netpoint.clinicapp.service;

import com.netpoint.clinicapp.DTO.StripeSessionResponseDTO;
import com.netpoint.clinicapp.Enum.PaymentMethod;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import com.netpoint.clinicapp.model.Bill;
import com.netpoint.clinicapp.repository.BillRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String secretKey;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final BillRepo billRepo;

    public StripeService(BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public StripeSessionResponseDTO createCheckoutSession(Long billId) throws StripeException {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));

        if (bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Bill is already paid");
        }

        // Convert BigDecimal amount to cents/smallest currency unit for Stripe (e.g., $50.00 -> 5000)
        long amountInCents = bill.getTotalAmount()
                .multiply(new BigDecimal("100"))
                .longValue();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendUrl + "/payment-success?billId=" + billId)
                .setCancelUrl(frontendUrl + "/payment-failed?billId=" + billId)
                .setClientReferenceId(billId.toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amountInCents)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Clinic Bill #" + billId)
                                                                .setDescription("Consultation fee payment")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        return new StripeSessionResponseDTO(session.getUrl(), session.getId());
    }


    @Transactional
    public void handleCheckoutSessionCompleted(Event event) {
        System.out.println("=== PROCESSING CHECKOUT SESSION COMPLETED ===");

        Session session = null;

        try {
            session = (Session) event.getDataObjectDeserializer()
                    .deserializeUnsafe();
        } catch (Exception e) {
            System.err.println("Failed to deserialize session: " + e.getMessage());
        }

        if (session == null || session.getClientReferenceId() == null) {
            System.err.println("Session or client_reference_id is null!");
            return;
        }

        if (session == null || session.getClientReferenceId() == null) {
            System.err.println("Session or client_reference_id is null!");
            return;
        }

        Long billId = Long.parseLong(session.getClientReferenceId());
        System.out.println("Target Bill ID: " + billId);

        Bill bill = billRepo.findById(billId).orElse(null);

        if (bill != null) {
            bill.setPaymentStatus(PaymentStatus.PAID);
            bill.setPaymentMethod(PaymentMethod.CREDIT_CARD);
            bill.setUpdatedAt(LocalDateTime.now());
            billRepo.save(bill);
            System.out.println(">>> SUCCESS! Bill #" + billId + " updated to PAID in PostgreSQL! <<<");
        } else {
            System.err.println("Bill ID " + billId + " not found in database.");
        }
    }
}