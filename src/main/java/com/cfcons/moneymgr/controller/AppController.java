package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.AccountDto;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.service.AccountService;
import com.cfcons.moneymgr.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class AppController {

    private final AccountService accountService;
    private final UserService userService;

    @GetMapping
    public String getApp(Model model, Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());

        List<AccountDto> accounts = accountService.findAllAccountsByUser(currentUser);

        model.addAttribute("accounts", accounts);

        return "app";
    }
}
