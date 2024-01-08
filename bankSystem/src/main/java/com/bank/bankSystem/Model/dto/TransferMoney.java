package com.bank.bankSystem.Model.dto;

import com.bank.bankSystem.Model.dto.ReceiverDetails;
import com.bank.bankSystem.Model.dto.SenderDetails;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferMoney {
    private SenderDetails senderDetails;
    private ReceiverDetails receiverDetails;
    private double transferMoney;
}
