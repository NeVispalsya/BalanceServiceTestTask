package com.testTask.BalanceServiceTestTask.controller;

import com.testTask.BalanceServiceTestTask.dto.GetTransactionResponse;
import com.testTask.BalanceServiceTestTask.dto.ReserveBalanceResponse;
import com.testTask.BalanceServiceTestTask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @GetMapping("/{userId}")
    public GetTransactionResponse getAllTransactionsByUserId(@PathVariable Long userId){
        return service.getHistoryTransactionsByUserId(userId);
    }
}
