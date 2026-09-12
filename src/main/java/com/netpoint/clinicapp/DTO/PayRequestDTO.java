package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.Enum.PaymentMethod;
import com.netpoint.clinicapp.Enum.PaymentStatus;
import lombok.Data;

@Data
public class PayRequestDTO {
    private PaymentMethod paymentMethod;

}
