package utils;

import java.io.*;
import java.util.*;

public class CSVReader {
    private static final String CSV_FILE_PATH = "src/test/resources/objectRepo/ObjectLocators.csv";

    public static Map<String, String> getLocator(String identifierName) {
        Map<String, String> locatorData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            int rowCount = 0;

            while ((line = br.readLine()) != null) {
                rowCount++;
                // System.out.println("Reading row " + rowCount + ": " + line);

                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();  // Clean whitespace
                }

                if (values.length >= 6) {
                    // System.out.println("Checking Identifier_Name: " + values[3]);

                    if (values[3].equalsIgnoreCase(identifierName)) {
                        // System.out.println("Match found for identifier: " + identifierName);
                        locatorData.put("locatorType", values[4]);
                        locatorData.put("locatorValue", values[5]);
                        // System.out.println("Locator Type: " + values[4] + ", Locator Value: " + values[5]);
                        break;
                    }
                } else {
                    // System.out.println("Skipping row " + rowCount + ": insufficient data columns");
                }
            }

            if (locatorData.isEmpty()) {
                System.out.println("No match found for identifier: " + identifierName);
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return locatorData;
    }
}
