package com.yorku.parking.model;

public class AccessibleSpaceDecorator extends ParkingSpaceDecorator {
    public AccessibleSpaceDecorator(IParkingSpace space) {
        super(space);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Accessible";
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
