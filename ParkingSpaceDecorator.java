package com.yorku.parking.model;

public abstract class ParkingSpaceDecorator implements IParkingSpace {
    protected IParkingSpace space; // ✅ Changed from private to protected

    public ParkingSpaceDecorator(IParkingSpace space) {
        this.space = space;
    }

    @Override
    public String getDescription() {
        return space.getDescription();
    }

    @Override
    public String getStatus() {
        return space.getStatus();
    }

    @Override
    public void enableSpace() {
        space.enableSpace();
    }

    @Override
    public void disableSpace() {
        space.disableSpace();
    }

    @Override
    public void addObserver(Observer observer) {
        space.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        space.removeObserver(observer);
    }

    @Override
    public void notifyObservers() {
        space.notifyObservers();
    }

    @Override
    public void markOccupied() {
        space.markOccupied();
    }

    @Override
    public boolean isOccupied() {
        return space.isOccupied();
    }

    @Override
    public String getId() {
        return space.getId();
    }
}
