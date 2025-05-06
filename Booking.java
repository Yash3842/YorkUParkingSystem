package com.yorku.parking.model;

import java.util.Date;

public class Booking {
    private Date startTime;
    private Date endTime;
    private Payment payment;
    private Client client;
    private IParkingSpace space;

    public Booking() {}

    public Booking(Client client, IParkingSpace space) {
        this.client = client;
        this.space = space;
        this.startTime = new Date();
    }

    public boolean extendBooking(int hours) {
        System.out.println("Booking extended by " + hours + " hours.");
        return true;
    }

    public boolean cancelBooking() {
        System.out.println("Booking cancelled.");
        return true;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Payment getPayment() {
        return payment;
    }

    public Client getClient() {
        return client;
    }

    public IParkingSpace getSpace() {
        return space;
    }
}
