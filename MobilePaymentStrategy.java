package com.yorku.parking.model;

public class MobilePaymentStrategy implements PaymentMethodStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Mobile Payment.");
        return true;
    }
}