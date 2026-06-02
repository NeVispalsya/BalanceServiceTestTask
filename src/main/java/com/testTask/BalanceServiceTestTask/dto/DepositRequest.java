package com.testTask.BalanceServiceTestTask.dto;

import java.math.BigDecimal;

public record DepositRequest(Long userId, BigDecimal amount) {
}
