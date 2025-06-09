package net.etf.project.util;

import net.etf.project.gui.MainApplication;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;
import net.etf.project.model.vehicles.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The VehicleLoader class is responsible for loading vehicle data from a CSV file
 * and adding the vehicles to the application's main vehicle list.
 * The vehicles can be of types Car, Bicycle, or Scooter. Each vehicle type has
 * specific attributes, and validation is performed to ensure data integrity.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class VehicleLoader
{
    /**
     * Loads vehicles from a CSV file located at the path specified in the MainApplication.
     * It validates the data for each line in the file, and if any mandatory fields
     * are missing or incorrect, the line is skipped.
     *
     * The method checks for:
     * - Missing or invalid fields.
     * - Duplicate vehicle IDs.
     * - Valid formats for date, price, range, and max speed.
     *
     * The method also categorizes vehicles into Car, Bicycle, and Scooter
     * based on the 'type' field in the CSV file.
     */
    public static void loadVehicles()
    {
        try(BufferedReader br = Files.newBufferedReader(Paths.get(MainApplication.VEHICLE_DATA_PATH))){
            String line;
            br.readLine();// Skip header
            while((line = br.readLine()) != null) {
                try {
                    String[] fields = line.split(",");

                    if (fields.length < 9) {
                        System.out.println("Not enough arguments in line: " + line + ". Line is skipped.");
                        continue;
                    }

                    String id = fields[0];
                    String manufacturer = fields[1];
                    String model = fields[2];
                    String type = fields[8];

                    // Check mandatory fields
                    if(id.isEmpty() || manufacturer.isEmpty() || model.isEmpty() || fields[4].isEmpty() || type.isEmpty())
                    {
                        System.out.println("Mandatory fields missing in line: " + line + " Line is skipped.");
                        continue;
                    }

                    boolean exists = MainApplication.vehicles.stream().anyMatch(v -> v.getVehicleID().equals(id));
                    if (exists) {
                        System.out.println("Vehicle with ID " + id + " already exists. Line is skipped.");
                        continue;
                    }

                    // Parse optional fields and validate data formats
                    Date purchaseDate = null;
                    if(!fields[3].isEmpty()) {
                        purchaseDate = parseDate(fields[3]);
                        if(purchaseDate == null)
                        {
                            System.out.println("Invalid date format for line: " + line + " Line is skipped.");
                            continue;
                        }
                    }
                    Double price = null;
                    try{
                        if(!fields[4].isEmpty())
                        {
                            price = Double.parseDouble(fields[4]);
                        }
                    }catch(NumberFormatException e)
                    {
                        System.out.println("Invalid price format for line: " + line + " Line is skipped.");
                        continue;
                    }

                    if(price == null)
                    {
                        System.out.println("Price can not be null. Line is skipped.");
                        continue;
                    }

                    Integer range = null;
                    try{
                        if(!fields[5].isEmpty())
                        {
                            range = Integer.parseInt(fields[5]);
                        }
                    }catch(NumberFormatException e)
                    {
                        System.out.println("Invalid range format for line: " + line + " Line is skipped.");
                        continue;
                    }

                    Integer maxSpeed = null;
                    try{
                        if(!fields[6].isEmpty())
                        {
                            maxSpeed = Integer.parseInt(fields[6]);
                        }
                    }catch(NumberFormatException e)
                    {
                        System.out.println("Invalid max speed format for line " + line + " Line is skipped.");
                        continue;
                    }
                    String description = fields[7].isEmpty() ? "No description available" : fields[7];

                    // Create specific vehicle objects based on type
                    Vehicle newVehicle;
                    if ("automobil".equalsIgnoreCase(type)) {
                        //if(purchaseDate == null || description.isEmpty() )
                        //{
                        //   System.out.println("Purchase date and description are required for cars. Line is skipped.");
                        //    continue;
                        //}
                        newVehicle = new Car(id, price, manufacturer, model, purchaseDate, description);
                    } else if ("bicikl".equalsIgnoreCase(type)) {
                        //if(range == null)
                        //{
                        //    System.out.println("Range is required for bicycles. Line is skipped.");
                        //    continue;
                        //}
                        if (range == null) {
                            range = 0;
                        }
                        newVehicle = new Bicycle(id, price, manufacturer, model, range);
                    } else if ("trotinet".equalsIgnoreCase(type)) {
                        //if(maxSpeed == null)
                        //{
                        //    System.out.println("Max speed is required for scooters. Line is skipped.");
                        //    continue;
                        //}
                        if (maxSpeed == null) {
                            maxSpeed = 0;
                        }
                        newVehicle = new Scooter(id, price, manufacturer, model, maxSpeed);
                    } else {
                        throw new IllegalArgumentException("Unknown type of vehicle: " + type);
                    }
                    MainApplication.vehicles.add(newVehicle);
                }catch (Exception e)
                {
                    System.out.println("Error in line " + line + " " + e.getMessage());
                }
            }
        }catch (IOException e)
        {
            System.err.println("Error reading file " + MainApplication.VEHICLE_DATA_PATH + e.getMessage());
        }
    }

    /**
     * Parses a date from a string. The expected format is "d.M.yyyy.".
     * If the date format is incorrect, it returns null.
     *
     * @param date the date string to parse
     * @return the parsed Date object, or null if the format is invalid
     */
    private static Date parseDate(String date)
    {
        try{
            return new SimpleDateFormat("d.M.yyyy.").parse(date);
        }catch (Exception e)
        {
            System.out.println("Incorrect date format.");
            return null;
        }
    }
}
