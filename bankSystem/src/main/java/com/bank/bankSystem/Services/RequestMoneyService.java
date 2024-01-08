package com.bank.bankSystem.Services;

import com.bank.bankSystem.Model.Bank;
import com.bank.bankSystem.Model.RequestMoney;
import com.bank.bankSystem.Repository.BankRepository;
import com.bank.bankSystem.Repository.RequestMoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestMoneyService {

    @Autowired
    private RequestMoneyRepository requestMoneyRepository;
    @Autowired
    private BankRepository bankRepository;

    public List<RequestMoney> getAllRequest(String accountNo) {
        return requestMoneyRepository.findAllBySenderAccountNo(accountNo);
    }

    public RequestMoney requestMoney(String senderAccountNo, String receiverAccountNo, double money) {
        RequestMoney requestMoney = new RequestMoney();
        requestMoney.setReceiverAccountNo(receiverAccountNo);
        requestMoney.setSenderAccountNo(senderAccountNo);
        requestMoney.setRequestMoney(money);
        requestMoney.setStatus("pending");
        return requestMoneyRepository.save(requestMoney);
    }

    public String approval(String senderAccountNo, String receiverAccountNo, String status) {
        if (status.equals("YES")  || status.equals("Yes") || status.equals("yes")) {
            Bank senderAccount = bankRepository.findByAcNo(senderAccountNo);
            Bank receiverAccount = bankRepository.findByAcNo(receiverAccountNo);
            double currentSenderBalance = senderAccount.getBalance();
            double currentReceiverBalance = receiverAccount.getBalance();

            RequestMoney requestMoney = requestMoneyRepository.findBySenderAccountNoAndReceiverAccountNoAndStatus(senderAccountNo, receiverAccountNo, "pending");

            if (currentSenderBalance < requestMoney.getRequestMoney()) {
                return "Paisa nathi atla khata ma";
            }

            currentSenderBalance -= requestMoney.getRequestMoney();
            currentReceiverBalance += requestMoney.getRequestMoney();

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

            RequestMoney requestUpdateMoney = new RequestMoney();
            requestUpdateMoney.setId(requestMoney.getId());
            requestUpdateMoney.setRequestMoney(requestMoney.getRequestMoney());
            requestUpdateMoney.setSenderAccountNo(senderAccountNo);
            requestUpdateMoney.setReceiverAccountNo(receiverAccountNo);
            requestUpdateMoney.setStatus("Accepted");

            requestMoneyRepository.save(requestUpdateMoney);

            return "Approval accepted";
        } else if (status.equals("NO") || status.equals("No") || status.equals("no")) {
            RequestMoney requestMoney = requestMoneyRepository.findBySenderAccountNoAndReceiverAccountNoAndStatus(senderAccountNo, receiverAccountNo, "pending");

            RequestMoney requestUpdateMoney = new RequestMoney();
            requestUpdateMoney.setId(requestMoney.getId());
            requestUpdateMoney.setRequestMoney(requestMoney.getRequestMoney());
            requestUpdateMoney.setSenderAccountNo(senderAccountNo);
            requestUpdateMoney.setReceiverAccountNo(receiverAccountNo);
            requestUpdateMoney.setStatus("Rejected");

            requestMoneyRepository.save(requestUpdateMoney);

            return "Approval denied";
        } else {
            return "Invalid Approval";
        }
    }
}
