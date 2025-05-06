package com.yorku.parking.controller;

import com.yorku.parking.model.Client;

public interface ClientFactory {
    Client createClient(String type);
}