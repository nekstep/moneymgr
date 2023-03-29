package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.security.AuthenticationFacade;
import com.cfcons.moneymgr.service.AccountService;
import com.cfcons.moneymgr.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class AppController {

    private final AccountService accountService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String getApp(Model model) {
        User currentUser = authenticationFacade.getCurrentUser();

        List<AccountDto> accounts = accountService.findAllAccountsByUser(currentUser);

        model.addAttribute("accounts", accounts);

        return "app/main";
    }

    @GetMapping("/nav/accounts/list")
    public String getNavAccountsList(Model model) {
        User currentUser = authenticationFacade.getCurrentUser();

        List<AccountDto> accounts = accountService.findAllAccountsByUser(currentUser);

        model.addAttribute("accounts", accounts);

        return "app/account :: nav-accounts-list";
    }

    @GetMapping("/account/details/{id}")
    public String getAccountDetails(@PathVariable Long id, Model model) {
        AccountDto accountDto = accountService.findAccountById(id);

        if (accountDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("account", accountDto);

        return "app/account :: main-account-info";
    }
}
