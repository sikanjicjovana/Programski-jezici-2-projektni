package net.etf.project.simulation;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.etf.project.gui.MainApplication;
import net.etf.project.gui.MapController;
import net.etf.project.model.vehicles.*;
import java.util.*;

/**
 * The CityMap class represents a grid-based map used to manage vehicle movements and their visualization.
 * It synchronizes vehicle additions and removals on the grid and updates the GUI through the MapController.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class CityMap
{
    /**
     * A 2D grid that stores lists of vehicles at specific locations.
     */
    private final List<Vehicle>[][] grid;

    /**
     * The controller responsible for updating the visual representation of the map in the GUI.
     */
    private final MapController mapController;

    /**
     * Constructs a CityMap with the given MapController and initializes the grid so that it can store more vehicles
     * on one field.
     *
     * @param mapController The controller that updates the GUI with vehicle movements.
     */
    public CityMap(MapController mapController)
    {
        this.mapController = mapController;
        grid = new ArrayList[MainApplication.MAP_WIDTH][MainApplication.MAP_HEIGHT];
        for(int i = 0; i < MainApplication.MAP_WIDTH; i++)
        {
            for(int j = 0; j < MainApplication.MAP_HEIGHT; j++)
            {
                grid[i][j] = new ArrayList<>();
            }
        }
    }

    /**
     * Adds a vehicle to the specified position on the grid and updates the GUI to reflect the vehicle's presence.
     * The method is synchronized to ensure thread safety.
     *
     * @param x The x-coordinate of the vehicle's position.
     * @param y The y-coordinate of the vehicle's position.
     * @param vehicle The vehicle to be added to the grid.
     */
    public synchronized void addVehicle(int x, int y, Vehicle vehicle)
    {
        grid[x][y].add(vehicle);

        String vehicleId = vehicle.getVehicleID();
        double batteryPercentage = vehicle.getCurrentBatteryLevel();

        Platform.runLater(() -> mapController.addVehicleToField(x, y, vehicleId, batteryPercentage));
    }

    /**
     * Removes a vehicle from the specified position on the grid and updates the GUI to reflect its removal.
     * The method is synchronized to ensure thread safety.
     *
     * @param x The x-coordinate of the vehicle's current position.
     * @param y The y-coordinate of the vehicle's current position.
     * @param vehicle The vehicle to be removed from the grid.
     */
    public synchronized void removeVehicle(int x, int y, Vehicle vehicle)
    {
        grid[x][y].remove(vehicle);

        Platform.runLater(() -> mapController.removeVehicleFromField(x, y));
    }

    /**
     * Displays a textual representation of the grid in the console.
     * Each cell either shows a dot (if empty) or the number of vehicles in that cell.
     */
    public void display()
    {
        for (int i = 0; i < MainApplication.MAP_WIDTH; i++)
        {
            for(int j = 0; j < MainApplication.MAP_HEIGHT; j++)
            {
                if(grid[i][j].isEmpty())
                {
                    System.out.print(".");
                }else{
                    System.out.print(grid[i][j].size());
                }
            }
            System.out.println();
        }
    }
}
