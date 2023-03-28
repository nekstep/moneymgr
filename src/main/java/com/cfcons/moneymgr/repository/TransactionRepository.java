package com.cfcons.moneymgr.repository;

import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);
}
