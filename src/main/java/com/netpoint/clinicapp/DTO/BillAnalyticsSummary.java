package com.netpoint.clinicapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillAnalyticsSummary {
    public Long totalBills;
    public Long totalUnpaidBills;
    public Long totalPaidBills;
    public BigDecimal totalRevenue;
}
