package com.testTask.BalanceServiceTestTask.entity;

import com.testTask.BalanceServiceTestTask.typeEnum.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private Long serviceId;
    @Column(unique = true, nullable = false)
    private Long orderId;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ReservationEntity() {
    }

    public ReservationEntity(Long userId,
                             Long serviceId,
                             Long orderId,
                             BigDecimal amount,
                             ReservationStatus status) {

        this.userId = userId;
        this.serviceId = serviceId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }
}