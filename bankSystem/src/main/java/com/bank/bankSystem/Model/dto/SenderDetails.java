package com.bank.bankSystem.Model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderDetails {
    private String accountNo;
    private String senderName;
}
