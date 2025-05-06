package com.yorku.parking.util;

import com.yorku.parking.model.*;

import java.text.SimpleDateFormat;

public class BookingCSVLogger {
    private static final String FILE_PATH = "logs/bookings.csv";

    public static void logBooking(Client client, IParkingSpace space) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String line = String.join(",",
                client.getEmail(),
                client.getUserType(),
                space.getId(),
                space.getDescription(),
                sdf.format(new java.util.Date())
        );
        CSVUtils.writeLine(FILE_PATH, line);
    }
}
