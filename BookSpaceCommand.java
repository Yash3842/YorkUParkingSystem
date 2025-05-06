package com.yorku.parking.model;

import com.yorku.parking.util.BookingCSVLogger;


public class BookSpaceCommand implements Command {
    private BookingManager bookingManager;
    private Client client;
    private IParkingSpace space;
    private Booking booking;

    public BookSpaceCommand(BookingManager manager, Client client, IParkingSpace space) {
        this.bookingManager = manager;
        this.client = client;
        this.space = space;
    }

    @Override
    public void execute() {
        booking = client.bookParking(space);
        space.markOccupied(); // ⬅️ Mark the space as occupied
        System.out.println("Space " + space.getDescription() + " booked by " + client.getEmail());
        BookingCSVLogger.logBooking(client, space);
    }



    @Override
    public void undo() {
        if (booking != null) {
            booking.cancelBooking();
            System.out.println("Booking for space " + space.getDescription() + " undone.");
        }
    }
}