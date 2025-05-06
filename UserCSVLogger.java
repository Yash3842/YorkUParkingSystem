package com.yorku.parking.util;

import com.yorku.parking.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserCSVLogger {
    private static final String FILE_PATH = "logs/users.csv";

    public static void logUser(User user) {
        String line = String.join(",",
                user.getEmail(),
                user.getUserType()
        );
        CSVUtils.writeLine(FILE_PATH, line);
    }

    public static boolean emailExists(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
      
            return false;
        }
        return false;
    }
}
