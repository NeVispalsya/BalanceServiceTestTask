package com.testTask.BalanceServiceTestTask.controller;

import com.testTask.BalanceServiceTestTask.dto.*;
import com.testTask.BalanceServiceTestTask.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("/deposit")
    public DepositResponse deposit(@RequestBody DepositRequest request){
        return balanceService.deposit(request.userId(), request.amount());
    }
    @GetMapping("/{userId}")
    public BalanceResponse getBalance(@PathVariable Long userId){
       return balanceService.getBalanceById(userId);
    }

    @PostMapping("/reserve")
    public ReserveBalanceResponse reserveBalance(@RequestBody ReserveRequest request){
       return balanceService.reserve(request.userId(),request.amount(),request.serviceId(),request.orderId());
    }

    @PostMapping("/confirm/{orderId}")
    public ConfirmResponse confirm(@PathVariable Long orderId){
        return balanceService.confirm(orderId);
    }

    @PostMapping("/unreserve/{orderId}")
    public UnreserveResponse unreserve(@PathVariable Long orderId){
        return balanceService.unreserve(orderId);
    }
}
