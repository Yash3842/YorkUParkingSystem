package com.yorku.parking.model;

import java.util.Date;

public class Visitor extends Client {
    public static final double HOURLY_RATE = 15.0;

    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }

    @Override
    public Booking bookParking(IParkingSpace space) {
        Booking booking = new Booking(this, space);
        booking.setStartTime(new Date());
        System.out.println("Visitor booked space: " + space.getDescription());
        return booking;
    }
}
