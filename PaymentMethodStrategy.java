package com.yorku.parking.model;

public interface PaymentMethodStrategy {
    boolean processPayment(double amount);
}