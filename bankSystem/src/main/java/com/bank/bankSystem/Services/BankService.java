package com.bank.bankSystem.Services;

import com.bank.bankSystem.Model.*;
import com.bank.bankSystem.Model.dto.*;
import com.bank.bankSystem.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;
    public List<Bank> adddetails(List<Bank> bank) {
        return bankRepository.saveAll(bank);
    }
    public Bank getDetails(String accountNo) {
        return bankRepository.findByAcNo(accountNo);
    }
    public AfterWithdrawBalance withdraw(Integer withdrawBalance, String accountNo) {
        Bank currentAccount = bankRepository.findByAcNo(accountNo);
        double currentBalance = currentAccount.getBalance();
        if (currentBalance < withdrawBalance) {
            return AfterWithdrawBalance.builder().withdraw(0).currentBalance(currentBalance).build();
        }
        currentBalance -= withdrawBalance;
        Bank updateAccount = new Bank();
        updateAccount.setId(currentAccount.getId());
        updateAccount.setBalance(currentBalance);
        updateAccount.setAcNo(currentAccount.getAcNo());
        updateAccount.setAcHolderName(currentAccount.getAcHolderName());
        bankRepository.save(updateAccount);
        return AfterWithdrawBalance.builder()
                .withdraw(withdrawBalance)
                .currentBalance(updateAccount.getBalance())
                .build();
    }

    public AfterDepositBalance deposit(Integer depositBalance, String accountNo) {
        Bank currentAccount = bankRepository.findByAcNo(accountNo);
        double currentBalance = currentAccount.getBalance();
        currentBalance += depositBalance;
        Bank updateAccount = new Bank();
        updateAccount.setId(currentAccount.getId());
        updateAccount.setBalance(currentBalance);
        updateAccount.setAcNo(currentAccount.getAcNo());
        updateAccount.setAcHolderName(currentAccount.getAcHolderName());
        bankRepository.save(updateAccount);
        return AfterDepositBalance.builder()
                .depositBalance(depositBalance)
                .currentBalance(updateAccount.getBalance())
                .build();
    }

    public TransferMoney transferMoney(Integer amount, String senderAccountNo, String receiverAccountNo) {
        Bank senderAccount = bankRepository.findByAcNo(senderAccountNo);
        Bank receiverAccount = bankRepository.findByAcNo(receiverAccountNo);
        double currentSenderBalance = senderAccount.getBalance();
        double currentReceiverBalance = receiverAccount.getBalance();

        if (currentSenderBalance < amount) {
            return TransferMoney.builder()
                    .transferMoney(0)
                    .senderDetails(
                            SenderDetails.builder()
                                    .accountNo(senderAccount.getAcNo())
                                    .senderName(senderAccount.getAcHolderName())
                                    .build()
                    )
                    .receiverDetails(
                            ReceiverDetails.builder()
                                    .accountNo(receiverAccount.getAcNo())
                                    .receiverName(receiverAccount.getAcHolderName())
                                    .build()
                    )
                    .build();
        }

        currentSenderBalance -= amount;
        currentReceiverBalance += amount;

        Bank updateSenderAccount = new Bank();
        updateSenderAccount.setId(senderAccount.getId());
        updateSenderAccount.setBalance(currentSenderBalance);
        updateSenderAccount.setAcNo(senderAccount.getAcNo());
        updateSenderAccount.setAcHolderName(senderAccount.getAcHolderName());
        bankRepository.save(updateSenderAccount);

        Bank updateReceiverAccount = new Bank();
        updateReceiverAccount.setId(receiverAccount.getId());
        updateReceiverAccount.setBalance(currentReceiverBalance);
        updateReceiverAccount.setAcNo(receiverAccount.getAcNo());
        updateReceiverAccount.setAcHolderName(receiverAccount.getAcHolderName());
        bankRepository.save(updateReceiverAccount);

        return TransferMoney.builder()
                .transferMoney(amount)
                .senderDetails(
                        SenderDetails.builder()
                                .accountNo(senderAccount.getAcNo())
                                .senderName(senderAccount.getAcHolderName())
                                .build()
                )
                .receiverDetails(
                        ReceiverDetails.builder()
                                .accountNo(receiverAccount.getAcNo())
                                .receiverName(receiverAccount.getAcHolderName())
                                .build()
                )
                .build();

    }
}
