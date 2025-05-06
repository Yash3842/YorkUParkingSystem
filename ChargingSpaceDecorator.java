package com.yorku.parking.model;

public class ChargingSpaceDecorator extends ParkingSpaceDecorator {
    public ChargingSpaceDecorator(IParkingSpace space) {
        super(space);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Charging Station";
    }

    @Override
    public void markOccupied() {
        space.markOccupied();
    }

    @Override
    public boolean isOccupied() {
        return space.isOccupied();
    }
}
