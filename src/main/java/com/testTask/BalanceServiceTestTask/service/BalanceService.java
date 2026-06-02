package com.testTask.BalanceServiceTestTask.service;

import com.testTask.BalanceServiceTestTask.dto.*;
import com.testTask.BalanceServiceTestTask.entity.BalanceEntity;
import com.testTask.BalanceServiceTestTask.entity.ReservationEntity;
import com.testTask.BalanceServiceTestTask.entity.TransactionEntity;
import com.testTask.BalanceServiceTestTask.exception.NotFoundException;
import com.testTask.BalanceServiceTestTask.repository.BalanceRepository;
import com.testTask.BalanceServiceTestTask.repository.ReservationRepository;
import com.testTask.BalanceServiceTestTask.repository.TransactionRepository;
import com.testTask.BalanceServiceTestTask.typeEnum.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final ReservationRepository reservationRepository;
    private final TransactionService transaction;

    public DepositResponse deposit(Long userId, BigDecimal amount){
        Optional<BalanceEntity> entity = balanceRepository.findById(userId);
        if (entity.isEmpty()) {
            BalanceEntity balanceEntity = new BalanceEntity();
            balanceEntity.setReservedBalance(new BigDecimal("0.00"));
            balanceEntity.setBalance(amount);
            balanceEntity.setUserId(userId);
            balanceRepository.save(balanceEntity);
            transaction.createTransactionDeposit(userId,amount);
        }else{
            entity.get().setBalance(entity.get().getBalance().add(amount));
            balanceRepository.save(entity.get());
            transaction.createTransactionDeposit(userId,amount);
        }
        return new DepositResponse(userId, amount,"Deposit access");
    }
    public BalanceResponse getBalanceById(Long userId){
        Optional<BalanceEntity> entity = balanceRepository.findById(userId);
        if (entity.isEmpty()){
            throw new NotFoundException();
        }else{
            return new BalanceResponse(entity.get().getUserId(),entity.get().getBalance(),entity.get().getReservedBalance());
        }
    }
    @Transactional
    public ReserveBalanceResponse reserve(Long userId, BigDecimal amount, Long serviceId, Long orderId){
        Optional<BalanceEntity> entity = balanceRepository.findById(userId);
        if (entity.isEmpty()){
            throw new NotFoundException();
        }else{
            if (entity.get().getBalance().compareTo(amount)>=0){
                    ReservationEntity reservationEntity = new ReservationEntity();
                    reservationEntity.setUserId(userId);
                    reservationEntity.setAmount(amount);
                    reservationEntity.setServiceId(serviceId);
                    reservationEntity.setOrderId(orderId);
                    reservationEntity.setStatus(ReservationStatus.RESERVED);
                try {
                    reservationRepository.save(reservationEntity);
                } catch (DataIntegrityViolationException e) {
                    throw new NotFoundException();
                }
                    BigDecimal a = entity.get().getBalance();
                    entity.get().setBalance(a.subtract(amount));
                    BigDecimal reserved = Optional.ofNullable(entity.get().getReservedBalance())
                        .orElse(BigDecimal.ZERO);
                    entity.get().setReservedBalance(reserved.add(amount));
                    balanceRepository.save(entity.get());
                    transaction.createTransactionReserve(userId, amount, serviceId, orderId);
                    return new ReserveBalanceResponse(userId,amount,serviceId,orderId);
            }else{
                throw new NotFoundException();
            }
        }
    }
    @Transactional
    public ConfirmResponse confirm(Long orderId){
       Optional<ReservationEntity> reservationEntity = reservationRepository.findByOrderId(orderId);
        if (reservationEntity.isEmpty()||
                reservationEntity.get().getStatus()!=ReservationStatus.RESERVED){
            throw new NotFoundException();
        }else{
            Optional<BalanceEntity> entity = balanceRepository.findById(reservationEntity.get().getUserId());
            if (entity.isEmpty()){
                throw new NotFoundException();
            }else{
                entity.get().setReservedBalance(entity.get().getReservedBalance().subtract(reservationEntity.get().getAmount()));
                reservationEntity.get().setStatus(ReservationStatus.CONFIRMED);
                balanceRepository.save(entity.get());
                reservationRepository.save(reservationEntity.get());
                transaction.createTransactionConfirm(entity.get().getUserId(),
                        reservationEntity.get().getAmount(),
                        reservationEntity.get().getServiceId(),
                        reservationEntity.get().getOrderId());
                return new ConfirmResponse(orderId,"Order "+orderId+" confirm!");
            }
        }
    }
    @Transactional
    public UnreserveResponse unreserve(Long orderId){
        Optional<ReservationEntity> reservationEntity = reservationRepository.findByOrderId(orderId);
        if (reservationEntity.isEmpty()||
                reservationEntity.get().getStatus()==ReservationStatus.CONFIRMED||
                reservationEntity.get().getStatus()==ReservationStatus.CANCELED){
            throw new NotFoundException();
        }else{
            Optional<BalanceEntity> balanceEntity = balanceRepository.findById(reservationEntity.get().getUserId());
            if (balanceEntity.isEmpty()){
                throw new NotFoundException();
            }else{
                BigDecimal a = balanceEntity.get().getBalance();
                balanceEntity.get().setBalance(a.add(reservationEntity.get().getAmount()));
                BigDecimal b = balanceEntity.get().getReservedBalance();
                balanceEntity.get().setReservedBalance(b.subtract(reservationEntity.get().getAmount()));
                reservationEntity.get().setStatus(ReservationStatus.CANCELED);
                balanceRepository.save(balanceEntity.get());
                reservationRepository.save(reservationEntity.get());
                transaction.createTransactionUnreserve(balanceEntity.get().getUserId(),
                        reservationEntity.get().getAmount(),
                        reservationEntity.get().getServiceId(),
                        reservationEntity.get().getOrderId());
                return new UnreserveResponse(orderId,"Order "+orderId+" unreserve!");
            }
        }
    }
}