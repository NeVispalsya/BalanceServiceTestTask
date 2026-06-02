package com.testTask.BalanceServiceTestTask.dto;

import com.testTask.BalanceServiceTestTask.entity.TransactionEntity;

import java.util.List;

public record GetTransactionResponse(Long userId, List<TransactionEntity> transactions) {
}
