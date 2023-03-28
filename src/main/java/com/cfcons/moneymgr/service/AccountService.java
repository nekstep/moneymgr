package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.User;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAllAccountsByUser(User user);

    Account findAccountById(Long Id);

    Account addAccount(User user, AccountDto accountDto);

    User getAuthorisedUser(Account account);
}
