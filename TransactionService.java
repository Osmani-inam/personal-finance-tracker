package com.inamul.financetracker.service;

import com.inamul.financetracker.dto.SummaryResponse;
import com.inamul.financetracker.model.Transaction;
import com.inamul.financetracker.model.TransactionType;
import com.inamul.financetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAll() {
        return repository.findAllByOrderByDateDesc();
    }

    public Transaction getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id " + id));
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public Transaction update(Long id, Transaction updated) {
        Transaction existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setAmount(updated.getAmount());
        existing.setType(updated.getType());
        existing.setCategory(updated.getCategory());
        existing.setDate(updated.getDate());
        existing.setNotes(updated.getNotes());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SummaryResponse getSummary() {
        List<Transaction> all = repository.findAll();

        double totalIncome = all.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = all.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Double> expenseByCategory = all.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

        return new SummaryResponse(totalIncome, totalExpense, totalIncome - totalExpense, expenseByCategory);
    }

    public static class NoSuchElementException extends RuntimeException {
        public NoSuchElementException(String message) {
            super(message);
        }
    }
}
