package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.TransactionDto;
import com.cfcons.moneymgr.security.AuthenticationFacade;
import com.cfcons.moneymgr.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/app/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/account/{id}")
    public String getAccountDetails(@PathVariable Long id, Model model) {
        List<TransactionDto> transactionDtoList = transactionService.findAllTransactionsByAccountId(id);

        // add to model
        model.addAttribute("transactions", transactionDtoList);

        return "app/transaction :: main-transaction-list";
    }
}
