package com.netpoint.clinicapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeSessionResponseDTO {
    private String checkoutUrl;
    private String sessionId;
}