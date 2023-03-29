package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.User;

import java.util.List;

public interface AccountService {
    /**
     * Find all accounts belonging to particular user
     *
     * @param user  User object
     * @return      List of AccountDto
     */
    List<AccountDto> findAllAccountsByUser(User user);

    /**
     * Find account by id
     *
     * @param Id    Account id
     * @return      AccountDto or null if not found
     */
    AccountDto findAccountDtoById(Long Id);

    /**
     * Find account by id
     *
     * @param Id    Account id
     * @return      AccountDto or null if not found
     */
    Account findAccountById(Long Id);

    /**
     * Update account name
     *
     * @param accountDto    AccountDto to update
     * @return              Updated AccountDto
     */
    AccountDto updateAccount(AccountDto accountDto);

    /**
     * Add new account
     * @param user          User to add account to
     * @param accountDto    AccountDto for new account
     * @return              Account object of newly created account
     */
    Account addAccount(User user, AccountDto accountDto);

    /**
     * Check if current user is authorized to access the account
     *
     * @param account   Account in question
     * @return          true if authorized
     */
    Boolean isCurrentUserAuthorised(Account account);
}
