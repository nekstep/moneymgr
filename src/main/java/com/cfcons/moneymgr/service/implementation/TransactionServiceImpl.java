package com.cfcons.moneymgr.service.implementation;

import com.cfcons.moneymgr.dto.TransactionDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.Transaction;
import com.cfcons.moneymgr.repository.TransactionRepository;
import com.cfcons.moneymgr.service.AccountService;
import com.cfcons.moneymgr.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;
    @Override
    public List<TransactionDto> findAllTransactionsByAccount(Account account) {
        List<Transaction> transactions = transactionRepository.findAllByAccount(account);

        return transactions.stream()
                .filter(this::isCurrentUserAuthorised)
                .map(this::mapToTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction addTransaction(Account account, TransactionDto transactionDto) {
        Transaction transaction = new Transaction();

        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    @Override
    public Boolean isCurrentUserAuthorised(Transaction transaction) {
        return transaction != null
                ? accountService.isCurrentUserAuthorised(transaction.getAccount())
                : false;
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setId(transaction.getId());

        return transactionDto;
    }
}
