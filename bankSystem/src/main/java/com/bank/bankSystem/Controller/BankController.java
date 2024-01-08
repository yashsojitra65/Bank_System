package com.bank.bankSystem.Controller;

import com.bank.bankSystem.Model.dto.AfterDepositBalance;
import com.bank.bankSystem.Model.dto.AfterWithdrawBalance;
import com.bank.bankSystem.Model.Bank;
import com.bank.bankSystem.Model.dto.TransferMoney;
import com.bank.bankSystem.Services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bank")
public class BankController {
    @Autowired
    BankService bankService;
    @PostMapping("add")
    private List<Bank> addDetails(@RequestBody List<Bank> bank) {
        return bankService.adddetails(bank);
    }

    @GetMapping("getDetails")
    private Bank getDetails(@RequestParam String accountNo) {
        return bankService.getDetails(accountNo);
    }

    @PostMapping("withdraw")
    private AfterWithdrawBalance withdraw(@RequestParam Integer withdraw, @RequestParam String accountNo) {
        return bankService.withdraw(withdraw, accountNo);
    }

    @PostMapping("deposit")
    private AfterDepositBalance deposit(@RequestParam Integer deposit, @RequestParam String accountNo) {
        return bankService.deposit(deposit, accountNo);
    }

    @PostMapping("transfer")
    private TransferMoney transferMoney(@RequestParam Integer amount, @RequestParam String senderAccountNo, @RequestParam String receiverAccountNo) {
        return bankService.transferMoney(amount, senderAccountNo, receiverAccountNo);
    }
}
