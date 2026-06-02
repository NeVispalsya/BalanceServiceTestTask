package com.testTask.BalanceServiceTestTask.service;

import com.testTask.BalanceServiceTestTask.dto.GetTransactionResponse;
import com.testTask.BalanceServiceTestTask.entity.TransactionEntity;
import com.testTask.BalanceServiceTestTask.repository.TransactionRepository;
import com.testTask.BalanceServiceTestTask.typeEnum.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public void createTransactionDeposit(Long userId,
                                         BigDecimal amount){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setComment("Deposit +"+amount.toString());
        transactionRepository.save(transaction);

    }
    public void createTransactionReserve(Long userId,
                                         BigDecimal amount,
                                         Long serviceId,
                                         Long orderId){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setServiceId(serviceId);
        transaction.setOrderId(orderId);
        transaction.setType(TransactionType.RESERVE);
        transaction.setComment("Reserve money +"+amount.toString());
        transactionRepository.save(transaction);
    }
    public void createTransactionConfirm(Long userId,
                                         BigDecimal amount,
                                         Long serviceId,
                                         Long orderId){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setServiceId(serviceId);
        transaction.setOrderId(orderId);
        transaction.setType(TransactionType.CONFIRM);
        transaction.setComment("Confirm reserve");
        transactionRepository.save(transaction);
    }
    public void createTransactionUnreserve(Long userId,
                                         BigDecimal amount,
                                         Long serviceId,
                                         Long orderId){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setServiceId(serviceId);
        transaction.setOrderId(orderId);
        transaction.setType(TransactionType.UNRESERVE);
        transaction.setComment("Unreserve ");
        transactionRepository.save(transaction);
    }
    public GetTransactionResponse getHistoryTransactionsByUserId(Long userId){
        List<TransactionEntity> transactions = new ArrayList<>(transactionRepository.findByUserId(userId));
        return new GetTransactionResponse(userId,transactions);
    }
}
//        Что будет делать
//        История операций:
//
//        pagination
//        sorting
//        filtering