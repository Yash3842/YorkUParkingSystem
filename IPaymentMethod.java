package com.yorku.parking.model;

public interface IPaymentMethod {
    int MAX_RETRIES = 3;
    boolean processPayment(double amount);
}
