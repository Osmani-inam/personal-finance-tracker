package com.inamul.financetracker.dto;

import java.util.Map;

public class SummaryResponse {

    private double totalIncome;
    private double totalExpense;
    private double balance;
    private Map<String, Double> expenseByCategory;

    public SummaryResponse(double totalIncome, double totalExpense, double balance, Map<String, Double> expenseByCategory) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.expenseByCategory = expenseByCategory;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Double> getExpenseByCategory() {
        return expenseByCategory;
    }
}
