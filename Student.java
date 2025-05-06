package com.yorku.parking.model;

import java.util.Date;

public class Student extends Client {
    public static final double HOURLY_RATE = 5.0;

    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }

    @Override
    public Booking bookParking(IParkingSpace space) {
        Booking booking = new Booking(this, space);
        booking.setStartTime(new Date());
        System.out.println("Student booked space: " + space.getDescription());
        return booking;
    }
}
