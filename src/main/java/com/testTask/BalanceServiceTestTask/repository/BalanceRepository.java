package com.testTask.BalanceServiceTestTask.repository;

import com.testTask.BalanceServiceTestTask.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity,Long> {
}
