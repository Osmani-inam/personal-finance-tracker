package com.inamul.financetracker.repository;

import com.inamul.financetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByOrderByDateDesc();
}
