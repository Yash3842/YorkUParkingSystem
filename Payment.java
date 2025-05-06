package com.yorku.parking.model;

public class Payment {
    private PaymentMethodStrategy strategy;
    private double deposit;
    private double total;

    public Payment(PaymentMethodStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean processPayment(double amount) {
        return strategy.processPayment(amount);
    }

    public void setTotal(double total) {
        this.total = total;
    }
}