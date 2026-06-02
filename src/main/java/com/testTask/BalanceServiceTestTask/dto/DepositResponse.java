package com.testTask.BalanceServiceTestTask.dto;

import java.math.BigDecimal;

public record DepositResponse(Long userId, BigDecimal amount, String message) {
}
