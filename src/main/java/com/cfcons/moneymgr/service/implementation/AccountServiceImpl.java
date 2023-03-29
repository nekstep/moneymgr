package com.cfcons.moneymgr.service.implementation;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.repository.AccountRepository;
import com.cfcons.moneymgr.security.AuthenticationFacade;
import com.cfcons.moneymgr.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to work with Account entities
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AuthenticationFacade authenticationFacade;

    /**
     * Find all accounts belonging to particular user
     *
     * @param user  User object
     * @return      List of AccountDto
     */
    @Override
    public List<AccountDto> findAllAccountsByUser(User user) {
        List<Account> accounts = accountRepository.findAllByUser(user);

        return accounts.stream()
                .filter(this::isCurrentUserAuthorised)
                .map(this::mapToAccountDto)
                .collect(Collectors.toList());
    }

    /**
     * Find account by id
     *
     * @param Id    Account id
     * @return      AccountDto or null if not found
     */
    @Override
    public AccountDto findAccountDtoById(Long Id) {
        return accountRepository.findById(Id)
                .filter(this::isCurrentUserAuthorised)
                .map(this::mapToAccountDto)
                .orElse(null);
    }

    @Override
    public Account findAccountById(Long Id) {
        return accountRepository.findById(Id)
                .filter(this::isCurrentUserAuthorised)
                .orElse(null);
    }

    /**
     * Update account name
     *
     * @param accountDto    AccountDto to update
     * @return              Updated AccountDto
     */
    @Override
    public AccountDto updateAccount(AccountDto accountDto) {

        // get Account if we are authorized to
        Account account = accountRepository.findById(accountDto.getId())
                .filter(this::isCurrentUserAuthorised)
                .orElse(null);

        // if no such account or not authorized - return null
        if (account == null) {
            return null;
        }

        // update account name
        account.setName(accountDto.getName());

        // return updated object
        return mapToAccountDto(accountRepository.save(account));
    }

    /**
     * Add new account
     * @param user          User to add account to
     * @param accountDto    AccountDto for new account
     * @return              Account object of newly created account
     */
    @Override
    public Account addAccount(User user, AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setUser(user);

        return accountRepository.save(account);
    }

    /**
     * Get account owner
     *
     * @param account   Account
     * @return          User or null
     */
    private User getAuthorisedUser(Account account) {
        return account != null
                ? account.getUser()
                : null;
    }

    /**
     * Check if current user is authorized to access the account
     *
     * @param account   Account in question
     * @return          true if authorized
     */
    @Override
    public Boolean isCurrentUserAuthorised(Account account) {
        if (account == null) {
            return false;
        }

        return getAuthorisedUser(account).equals(authenticationFacade.getCurrentUser());
    }

    /**
     * Map Account to AccountDto
     *
     * @param account   Account
     * @return          AccountDto
     */
    private AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setName(account.getName());

        return accountDto;
    }
}
