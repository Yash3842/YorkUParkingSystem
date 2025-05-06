package com.yorku.parking.model;

public class SuperManager extends Manager {
    private static SuperManager instance;

    private SuperManager() {
        // Private constructor for Singleton
    }

    public static SuperManager getInstance() {
        if (instance == null) {
            instance = new SuperManager();
        }
        return instance;
    }

    public Manager generateManagementAccount() {
        Manager manager = new Manager();
        System.out.println("SuperManager created a new manager account.");
        return manager;
    }
}