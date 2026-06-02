package com.testTask.BalanceServiceTestTask.dto;

import java.math.BigDecimal;

public record ReserveRequest(Long userId, BigDecimal amount, Long serviceId, Long orderId) {
}
