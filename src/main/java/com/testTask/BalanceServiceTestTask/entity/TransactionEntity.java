package com.testTask.BalanceServiceTestTask.entity;

import com.testTask.BalanceServiceTestTask.typeEnum.TransactionType;
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
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    private Long serviceId;
    private Long orderId;
    private String comment;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public TransactionEntity() {
    }

    public TransactionEntity(Long userId,
                             TransactionType type,
                             BigDecimal amount,
                             Long serviceId,
                             Long orderId,
                             String comment) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.serviceId = serviceId;
        this.orderId = orderId;
        this.comment = comment;
    }
}
