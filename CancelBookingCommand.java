package com.yorku.parking.model;

public class CancelBookingCommand implements Command {
    private BookingManager bookingManager;
    private Booking booking;
    private IParkingSpace space;

    public CancelBookingCommand(BookingManager manager, Booking booking, IParkingSpace space) {
        this.bookingManager = manager;
        this.booking = booking;
        this.space = space;
    }

    @Override
    public void execute() {
        booking.cancelBooking();
        System.out.println("Booking cancelled for space " + space.getDescription());
    }

    @Override
    public void undo() {
        System.out.println("Cancellation undone for space " + space.getDescription());
    }
}