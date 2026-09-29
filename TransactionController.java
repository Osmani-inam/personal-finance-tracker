package com.inamul.financetracker.controller;

import com.inamul.financetracker.dto.SummaryResponse;
import com.inamul.financetracker.model.Transaction;
import com.inamul.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Transaction getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@Valid @RequestBody Transaction transaction) {
        return service.create(transaction);
    }

    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
        return service.update(id, transaction);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/summary")
    public SummaryResponse getSummary() {
        return service.getSummary();
    }

    @ExceptionHandler(TransactionService.NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(TransactionService.NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
