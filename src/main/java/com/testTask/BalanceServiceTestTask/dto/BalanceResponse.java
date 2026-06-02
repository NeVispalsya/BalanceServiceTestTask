package com.testTask.BalanceServiceTestTask.dto;

import java.math.BigDecimal;

public record BalanceResponse(Long userId,
                              BigDecimal balance,
                              BigDecimal reservedBalance) {
}
