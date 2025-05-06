package com.yorku.parking.model;

public class BookingManager {
    public void executeCommand(Command cmd) {
        cmd.execute();
    }
}