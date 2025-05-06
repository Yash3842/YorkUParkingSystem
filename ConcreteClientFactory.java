package com.yorku.parking.controller;

import com.yorku.parking.model.*;

public class ConcreteClientFactory implements ClientFactory {
    @Override
    public Client createClient(String type) {
        switch (type) {
            case "Student":
                return new Student();
            case "Faculty":
                return new Faculty();
            case "NonFacultyStaff":
                return new NonFacultyStaff();
            case "Visitor":
                return new Visitor();
            default:
                throw new IllegalArgumentException("Unknown client type: " + type);
        }
    }
}