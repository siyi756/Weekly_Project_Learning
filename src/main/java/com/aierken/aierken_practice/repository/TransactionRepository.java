package com.aierken.aierken_practice.repository;

import com.aierken.aierken_practice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountNumberOrderByCreatedAtDesc(String accountNumber);
}
