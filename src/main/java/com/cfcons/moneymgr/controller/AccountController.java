package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.security.AuthenticationFacade;
import com.cfcons.moneymgr.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to process account related requests
 */
@Controller
@AllArgsConstructor
@RequestMapping("/app/account")
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * Get list of accounts for sidebar
     *
     * @param model Model
     * @return      View to display all accounts in sidebar
     */
    @GetMapping("/nav")
    public String getNavAccountsList(Model model) {
        User currentUser = authenticationFacade.getCurrentUser();

        // sort all accounts of current user by name
        List<AccountDto> accounts = accountService.findAllAccountsByUser(currentUser).stream()
                .sorted()
                .collect(Collectors.toList());

        // add to model
        model.addAttribute("accounts", accounts);

        return "app/account :: nav-accounts-list";
    }

    /**
     * Retrieve detals of account for account details view
     *
     * @param id        ID of account
     * @param model     Model
     * @return          View to display account details
     */
    @GetMapping("/details/{id}")
    public String getAccountDetails(@PathVariable Long id, Model model) {
        AccountDto accountDto = accountService.findAccountById(id);

        // check if this account actually exists
        if (accountDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // add to model
        model.addAttribute("account", accountDto);

        // TODO: Get transaction list from account

        return "app/account :: main-account-info";
    }

    /**
     * Display add account form
     *
     * @return  View to form
     */
    @GetMapping("/add")
    public String getAccountAddForm() {
        return "app/account :: main-account-addform";
    }

    /**
     * Process ajax post to create a new account
     *
     * @param request   AccountDto object from web page
     * @return          Updated AccountDto with id field set
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody AccountDto addAccount(@Valid AccountDto request) {
        Account account = accountService.addAccount(authenticationFacade.getCurrentUser(), request);

        // pass an id of newly created account back to webpage
        request.setId(account.getId());

        return request;
    }

    /**
     * Update account name
     *
     * @param request   AccountDto of account to be updated
     * @return          Updated version of AccountDto
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody AccountDto updateAccount(@Valid AccountDto request) {
        AccountDto accountDto = accountService.updateAccount(request);

        if (accountDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return accountDto;
    }
}
