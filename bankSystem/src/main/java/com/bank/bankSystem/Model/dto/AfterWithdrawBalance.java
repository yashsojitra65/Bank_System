package com.bank.bankSystem.Model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AfterWithdrawBalance {
    private double withdraw;
    private double currentBalance;
}
