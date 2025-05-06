package com.yorku.parking.model;

public interface IParkingSpace {
    String getId(); // ✅ ADDED
    String getDescription();
    String getStatus();
    void enableSpace();
    void disableSpace();
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    public void markOccupied();
    public boolean isOccupied();
}
