package net.etf.project.serialization;

import net.etf.project.model.vehicles.*;
import java.io.*;
import java.util.*;

/**
 * This class handles the serialization and deserialization of Vehicle objects.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class VehicleSerializer
{
    /**
     * Serializes a single Vehicle object to a binary file.
     *
     * @param vehicle The vehicle to be serialized.
     * @param outputFolder The folder where the serialized file will be saved.
     */
    public static void serializeVehicle(Vehicle vehicle, String outputFolder)
    {
        String fileName = outputFolder + File.separator + vehicle.getVehicleID() + ".bin";

        File file = new File(fileName);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(vehicle);
            System.out.println("Vehicle with ID " + vehicle.getVehicleID() + " successfully serialised.");
        }catch(IOException e)
        {
            System.err.println("Error serializing vehicle with ID " + vehicle.getVehicleID() + e.getMessage());
        }
    }

    /**
     * Serializes a list of vehicles that have malfunctions to individual binary files.
     *
     * @param vehicles A list of vehicles to be serialized.
     * @param outputFolder The folder where the serialized files will be saved.
     */
    public static void serializeVehiclesWithMalfunctions(List<Vehicle> vehicles, String outputFolder)
    {
        for(Vehicle v : vehicles)
            serializeVehicle(v, outputFolder);
    }

    /**
     * Deserializes a single Vehicle object from a binary file.
     *
     * @param filePath The path to the file from which the vehicle will be deserialized.
     * @return The deserialized Vehicle object, or null if an error occurs.
     */
    public static Vehicle deserializeVehicle(String filePath)
    {
        Vehicle vehicle = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            vehicle = (Vehicle) ois.readObject();
            System.out.println("Vehicle with ID: " + vehicle.getVehicleID() + " successfully deserialized from file " + filePath);
        }catch(IOException | ClassNotFoundException e)
        {
            System.err.println("Error deserializing vehicle from file: " + filePath + e.getMessage());
        }
        return vehicle;
    }

    /**
     * Deserializes all Vehicle objects from a folder containing binary files.
     *
     * @param inputFolder The folder containing the binary files for deserialization.
     * @return A list of deserialized Vehicle objects.
     */
    public static List<Vehicle> deserializeVehicles(String inputFolder)
    {
        List<Vehicle> vehicles = new ArrayList<>();
        File folder = new File(inputFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".bin"));

        if(files != null)
        {
            for(File file : files)
            {
                Vehicle vehicle = deserializeVehicle(file.getAbsolutePath());
                if(vehicle != null)
                {
                    vehicles.add(vehicle);
                }
            }
        }
        return vehicles;
    }
}
