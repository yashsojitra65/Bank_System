package com.bank.bankSystem.Repository;

import com.bank.bankSystem.Model.RequestMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestMoneyRepository extends JpaRepository<RequestMoney, Integer> {
    List<RequestMoney> findAllBySenderAccountNo(String accountNo);

    RequestMoney findBySenderAccountNoAndReceiverAccountNoAndStatus(String senderAccountNo, String receiverAccountNo, String pending);
}
