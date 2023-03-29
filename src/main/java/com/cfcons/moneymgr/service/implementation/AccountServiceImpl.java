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

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<AccountDto> findAllAccountsByUser(User user) {
        List<Account> accounts = accountRepository.findAllByUser(user);

        return accounts.stream()
                .filter(this::isCurrentUserAuthorised)
                .map(this::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findAccountById(Long Id) {
        return accountRepository.findById(Id)
                .filter(this::isCurrentUserAuthorised)
                .map(this::mapToAccountDto)
                .orElse(null);
    }

    @Override
    public Account addAccount(User user, AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setUser(user);

        return accountRepository.save(account);
    }

    private User getAuthorisedUser(Account account) {
        return account != null
                ? account.getUser()
                : null;
    }

    @Override
    public Boolean isCurrentUserAuthorised(Account account) {
        if (account == null) {
            return false;
        }

        return getAuthorisedUser(account).equals(authenticationFacade.getCurrentUser());
    }

    private AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setName(account.getName());

        return accountDto;
    }
}
