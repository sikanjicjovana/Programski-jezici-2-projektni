package net.etf.project.util;

import net.etf.project.model.rental.*;
import net.etf.project.model.vehicles.*;
import net.etf.project.gui.MainApplication;

import java.util.*;

/**
 * The VehicleUtils class provides utility methods for working with vehicles.
 * This includes finding specific vehicle-related data, such as vehicles with malfunctions.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class VehicleUtils
{
    /**
     * This method finds and returns a list of vehicles that have had malfunctions
     * during rentals. It iterates through the list of rentals in the MainApplication
     * and checks if the vehicle associated with each rental has a malfunction.
     *
     * If the rental's vehicle has a malfunction, the vehicle is added to the list
     * of malfunctioning vehicles.
     *
     * @return a list of vehicles that have experienced malfunctions
     */
    public static List<Vehicle> findVehiclesWithMalfunctions()
    {
        List<Vehicle> vehiclesWithMalfunctions = new ArrayList<>();
        for(Rental rental : MainApplication.rentals)
        {
            Vehicle vehicle = rental.findVehicleById();
            if(rental.isHasMalfunction())
            {
                vehiclesWithMalfunctions.add(vehicle);
            }
        }
        return vehiclesWithMalfunctions;
    }
}
