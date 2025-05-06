package com.yorku.parking.model;

public interface Command {
    void execute();
    void undo();
}