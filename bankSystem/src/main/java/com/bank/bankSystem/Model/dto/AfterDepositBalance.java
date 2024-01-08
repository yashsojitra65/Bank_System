package com.bank.bankSystem.Model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AfterDepositBalance {
    private double depositBalance;
    private double currentBalance;
}
