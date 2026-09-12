package com.netpoint.clinicapp.controller;

import com.netpoint.clinicapp.DTO.StripeSessionResponseDTO;
import com.netpoint.clinicapp.service.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final StripeService stripeService;

    public StripeWebhookController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    // Endpoint 1: Create Checkout Session
    @PostMapping("/{id}/create-checkout-session")
    public ResponseEntity<StripeSessionResponseDTO> createCheckoutSession(@PathVariable Long id) throws StripeException {
        return ResponseEntity.ok(stripeService.createCheckoutSession(id));
    }

    // Endpoint 2: Stripe Webhook Listener
    @PostMapping("/stripe-webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        System.out.println("=== STRIPE WEBHOOK RECEIVED ===");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println("Event Type: " + event.getType());
        } catch (SignatureVerificationException e) {
            System.err.println("Signature verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            System.err.println("Webhook parse error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook parse error");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            System.out.println("Processing checkout.session.completed...");
            stripeService.handleCheckoutSessionCompleted(event);
        }

        return ResponseEntity.ok("Success");
    }
}