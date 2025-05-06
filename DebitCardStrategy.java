package com.yorku.parking.model;

public class DebitCardStrategy implements PaymentMethodStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Debit Card.");
        return true;
    }
}