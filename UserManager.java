package com.yorku.parking.controller;

import com.yorku.parking.model.*;
import java.util.ArrayList;
import java.util.List;
import com.yorku.parking.util.*;

public class UserManager {
    private final List<User> users = new ArrayList<>();
    private final ClientFactory clientFactory = new ConcreteClientFactory();
    private final List<ParkingLot> parkingLots = new ArrayList<>();
    private User loggedInUser;

    public UserManager() {
        ParkingLot lot1 = new ParkingLot("Lot A", "Main Campus");
        ParkingLot lot2 = new ParkingLot("Lot B", "West Campus");
        
        SuperManager superManager = SuperManager.getInstance();
        superManager.setEmail("admin@yorku.ca");
        superManager.setPassword("admin123");
        superManager.setUserType("SuperManager");
        users.add(superManager);


        // 🚗 Lot A – 100 spaces
        for (int i = 1; i <= 100; i++) {
            Sensor sensor = new Sensor("S" + i);
            IParkingSpace baseSpace = new ParkingSpace("A" + i, sensor);

            IParkingSpace finalSpace;
            if (i % 10 == 0) {
                finalSpace = new ChargingSpaceDecorator(baseSpace);
            } else if (i % 5 == 0) {
                finalSpace = new AccessibleSpaceDecorator(baseSpace);
            } else {
                finalSpace = baseSpace;
            }

            if (i % 17 == 0) {
                finalSpace.disableSpace();
            }

            lot1.addSpace(finalSpace);
        }

        // 🚗 Lot B – 100 spaces
        for (int i = 1; i <= 100; i++) {
            Sensor sensor = new Sensor("S" + (100 + i));
            IParkingSpace baseSpace = new ParkingSpace("B" + i, sensor);

            IParkingSpace finalSpace;
            if (i % 7 == 0) {
                finalSpace = new ChargingSpaceDecorator(baseSpace);
            } else if (i % 6 == 0) {
                finalSpace = new AccessibleSpaceDecorator(baseSpace);
            } else {
                finalSpace = baseSpace;
            }

            if (i % 15 == 0) {
                finalSpace.disableSpace();
            }

            lot2.addSpace(finalSpace);
        }

        parkingLots.add(lot1);
        parkingLots.add(lot2);
    }

    public boolean registerClient(String email, String password, String type) {
        if (UserCSVLogger.emailExists(email)) {
            return false; // email already exists
        }

        Client client = clientFactory.createClient(type);
        client.setEmail(email);
        client.setPassword(password);
        client.setUserType(type);
        users.add(client);

        UserCSVLogger.logUser(client);
        System.out.println("✅ Registered " + type + ": " + email);
        return true;
    }


    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.login(password)) {
                this.loggedInUser = user; // ✅ Save the session
                return user;
            }
        }
        return null;
    }


    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public ParkingLot getParkingLotByName(String name) {
        for (ParkingLot lot : parkingLots) {
            if (lot.getName().equals(name)) {
                return lot;
            }
        }
        return null;
    }
    
    public boolean registerManager(String email, String password) {
        if (UserCSVLogger.emailExists(email)) return false;

        Manager manager = new Manager();
        manager.setEmail(email);
        manager.setPassword(password);
        manager.setUserType("Manager");
        users.add(manager);
        UserCSVLogger.logUser(manager);
        return true;
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }


    
}



