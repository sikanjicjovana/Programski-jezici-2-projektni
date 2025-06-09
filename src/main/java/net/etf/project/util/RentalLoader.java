package net.etf.project.util;

import net.etf.project.model.rental.Location;
import net.etf.project.model.rental.Rental;
import net.etf.project.gui.MainApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The {@code RentalLoader} class is responsible for loading rental data from a CSV file and
 * populating the application's list of rentals. It reads each line from the file, parses the data,
 * and creates {@code Rental} objects, ensuring data validation at each step.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class RentalLoader
{
    /**
     * Loads rental data from a file located at {@code MainApplication.RENTAL_DATA_PATH}.
     * The data is read line by line, and for each line, a {@code Rental} object is created
     * if all required fields are valid. The rentals are then sorted by rental date and added to
     * the application's list of rentals. If any line contains invalid or incomplete data, it is skipped.
     */
    public static void loadRentals()
    {
        try(BufferedReader br = Files.newBufferedReader(Paths.get(MainApplication.RENTAL_DATA_PATH)))
        {
            String line;
            br.readLine();// Skip header

            while((line = br.readLine()) != null)
            {
                try{
                    // Split CSV line while ignoring commas inside quoted fields
                    String[] fields = line.split(",(?=(?:[^\"]|\"[^\"]*\")*$)");
                    if(fields.length < 8)
                    {
                        System.out.println("Not enough arguments in line: " + line + ". Line is skipped.");
                        continue;
                    }

                    // Check if any field is empty
                    boolean allFieldsFilled = true;
                    for (String field : fields) {
                        if (field.trim().isEmpty()) {
                            allFieldsFilled = false;
                            break;
                        }
                    }

                    if(!allFieldsFilled)
                    {
                        System.out.println("Some fields are empty in line: " + line + " Line is skipped.");
                        continue;
                    }

                    // Parse the rental data
                    Date rentalDateTime = parseDateAndTime(fields[0]);
                    if(rentalDateTime == null)
                    {
                        System.out.println("Invalid date format in line. " + line + " Line is skipped.");
                        continue;
                    }

                    String user = fields[1];
                    String id = fields[2];

                    // Validate if vehicle exists
                    boolean vehicleExists = MainApplication.vehicles.stream().anyMatch(v -> v.getVehicleID().equals(id));
                    if(!vehicleExists)
                    {
                        System.out.println("Vehicle with ID " + id + " does not exist. Line is skipped.");
                        continue;
                    }

                    // Parse start and end locations
                    Location startLocation = parseLocation(fields[3]);
                    if(startLocation == null)
                    {
                        System.out.println("Invalid start location format in line: " + line + " Line is skipped.");
                        continue;
                    }
                    Location endLocation = parseLocation(fields[4]);
                    if(endLocation == null)
                    {
                        System.out.println("Invalid end location format in line: " + line + " Line is skipped.");
                        continue;
                    }

                    // Parse rental duration
                    double rentalDuration;
                    try {
                        rentalDuration = Double.parseDouble(fields[5]);
                    }catch(NumberFormatException e)
                    {
                        System.out.println("Invalid rental duration format in line: " + line + " Line is skipped.");
                        continue;
                    }

                    // Parse malfunction and promotion checks
                    String malfunctionChecker = fields[6];
                    String promotionChecker = fields[7];

                    if(!"da".equalsIgnoreCase(malfunctionChecker) && !"ne".equalsIgnoreCase(malfunctionChecker))
                    {
                        System.out.println("Invalid string. Line is skipped.");
                        continue;
                    }
                    if(!"da".equalsIgnoreCase(promotionChecker) && !"ne".equalsIgnoreCase(promotionChecker))
                    {
                        System.out.println("Invalid string. Line is skipped.");
                        continue;
                    }

                    boolean hasMalfunction = "da".equalsIgnoreCase(malfunctionChecker);
                    boolean hasPromotion = "da".equalsIgnoreCase(promotionChecker);

                    // Create the new rental
                    Rental newRental = new Rental(rentalDateTime,user,id,startLocation,endLocation,rentalDuration,hasMalfunction,hasPromotion);

                    // Ensure the rental doesn't already exist
                    boolean rentalExists = MainApplication.rentals.stream().anyMatch(r -> r.equals(newRental));
                    if(rentalExists)
                    {
                        System.out.println("Rental with ID: " + id + " and date: " + rentalDateTime + " already exists. Line is skipped.");
                        continue;
                    }

                    // If there is a malfunction, add it to the vehicle's record
                    if(hasMalfunction)
                    {
                        MainApplication.vehicles.stream().filter(v -> v.getVehicleID().equals(id)).findFirst().ifPresent(v -> v.addMalfunction(rentalDateTime));
                    }

                    // Add rental to the list and sort the rentals by date
                    MainApplication.rentals.add(newRental);
                    MainApplication.rentals.sort(Comparator.comparing(Rental::getRentalDateTime));

                }catch(Exception e)
                {
                    System.out.println("Error in line: " + line + " " + e.getMessage());
                }
            }

        }catch(IOException e)
        {
            System.err.println("Error reading file " + MainApplication.RENTAL_DATA_PATH + " " + e.getMessage());
        }
    }

    /**
     * Parses a date and time string in the format "d.M.yyyy HH:mm" and returns a {@code Date} object.
     *
     * @param dateTime the string containing the date and time
     * @return the {@code Date} object, or {@code null} if the format is invalid
     */
    private static Date parseDateAndTime(String dateTime)
    {
        try{
            return new SimpleDateFormat("d.M.yyyy HH:mm").parse(dateTime);
        }catch (Exception e)
        {
            System.out.println("Incorrect date format.");
            return null;
        }
    }

    /**
     * Parses a location string in the format "x,y" and returns a {@code Location} object.
     *
     * @param location the string containing the coordinates
     * @return the {@code Location} object, or {@code null} if the format is invalid
     */
    private static Location parseLocation(String location)
    {
        try {
            location = location.replace("\"", "");
            String[] coords = location.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            if(x < 0 || x >= MainApplication.MAP_WIDTH || y < 0 || y >= MainApplication.MAP_HEIGHT)
            {
                System.out.println("Coordinates out of bound.");
                return null;
            }
            return new Location(x, y);
        }catch(Exception e)
        {
            System.out.println("Invalid location format.");
            return null;
        }
    }
}
