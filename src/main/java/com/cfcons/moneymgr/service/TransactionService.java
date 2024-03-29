package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.TransactionDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> findAllTransactionsByAccount(Account account);
    List<TransactionDto> findAllTransactionsByAccountId(Long id);

    Transaction addTransaction(TransactionDto transactionDto);

    Boolean isCurrentUserAuthorised(Transaction transaction);
}
