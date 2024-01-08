package com.bank.bankSystem.Repository;

import com.bank.bankSystem.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {
    @Query(value = "SELECT * FROM bank WHERE ac_no=:accountNo", nativeQuery = true)
    Bank findByAcNo(String accountNo);
}
