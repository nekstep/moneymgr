package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.TransactionDto;
import com.cfcons.moneymgr.entity.Transaction;
import com.cfcons.moneymgr.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/app/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/account/{id}")
    public String getAccountDetails(@PathVariable Long id, Model model) {
        List<TransactionDto> transactionDtoList = transactionService.findAllTransactionsByAccountId(id);

        // add to model
        model.addAttribute("transactions", transactionDtoList);

        return "app/transaction :: main-transaction-list";
    }

    /**
     * Process ajax post to create a new transaction
     *
     * @param request   TransactionDto object from web page
     * @return          Updated TransactionDto with id field set
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody TransactionDto addTransaction(@Valid TransactionDto request) {
        // Account account = accountService.addAccount(authenticationFacade.getCurrentUser(), request);
        Transaction transaction = transactionService.addTransaction(request);

        // pass an id of newly created account back to webpage
        if (transaction != null) {
            request.setId(transaction.getId());

            return request;
        }

        return null;
    }
}
