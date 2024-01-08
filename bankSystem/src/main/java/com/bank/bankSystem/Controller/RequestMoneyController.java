package com.bank.bankSystem.Controller;

import com.bank.bankSystem.Model.RequestMoney;
import com.bank.bankSystem.Services.RequestMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("request")
public class RequestMoneyController {
    @Autowired
    private RequestMoneyService requestMoneyService;
    @GetMapping("getAllRequest")
    private List<RequestMoney> getAllRequest(@RequestParam String accountNo) {
        return requestMoneyService.getAllRequest(accountNo);
    }
    @PostMapping("requestMoney")
    private RequestMoney requestMoney(@RequestParam String senderAccountNo, @RequestParam String receiverAccountNo, @RequestParam double money) {
        return requestMoneyService.requestMoney(senderAccountNo, receiverAccountNo, money);
    }
    @PostMapping("approval")
    private String approval(@RequestParam String senderAccountNo, @RequestParam String receiverAccountNo, @RequestParam String status) {
        return requestMoneyService.approval(senderAccountNo, receiverAccountNo, status);
    }
}
