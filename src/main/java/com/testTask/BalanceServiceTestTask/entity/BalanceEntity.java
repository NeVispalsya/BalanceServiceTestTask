package com.testTask.BalanceServiceTestTask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "balances")
public class BalanceEntity {
    @Id
    private Long userId;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal reservedBalance;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public BalanceEntity() {
    }

    public BalanceEntity(Long userId,
                         BigDecimal balance,
                         BigDecimal reservedBalance) {
        this.userId = userId;
        this.balance = balance;
        this.reservedBalance = reservedBalance;
    }
}
