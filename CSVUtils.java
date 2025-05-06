package com.yorku.parking.util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVUtils {
    public static void writeLine(String filePath, String line) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
