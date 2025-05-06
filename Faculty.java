package com.yorku.parking.model;

import java.util.Date;

public class Faculty extends Client {
    public static final double HOURLY_RATE = 8.0;

    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }

    @Override
    public Booking bookParking(IParkingSpace space) {
        Booking booking = new Booking();
        booking.setStartTime(new Date());
        System.out.println("Faculty booked space: " + space.getDescription());
        return booking;
    }
}