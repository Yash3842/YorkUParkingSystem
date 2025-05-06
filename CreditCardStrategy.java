package com.yorku.parking.model;

public class CreditCardStrategy implements PaymentMethodStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Credit Card.");
        return true;
    }
}