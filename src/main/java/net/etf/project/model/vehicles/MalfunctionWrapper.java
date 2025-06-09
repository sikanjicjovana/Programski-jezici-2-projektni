package net.etf.project.model.vehicles;

import java.util.Date;

/**
 * This class serves as a wrapper for malfunction data to be displayed in a GUI table.
 * It contains the type of vehicle, vehicle ID, description of the malfunction, and the date and time of the malfunction.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class MalfunctionWrapper
{
    /** The type of the vehicle (e.g., Car, Bicycle, Scooter). */
    private String type;

    /** The unique identifier for the vehicle. */
    private String vehicleID;

    /** A brief description of the malfunction. */
    private String description;

    /** The date and time when the malfunction occurred. */
    private Date dateAndTime;

    /**
     * Constructs a new MalfunctionWrapper with the given details.
     *
     * @param type        The type of the vehicle.
     * @param vehicleID   The unique identifier of the vehicle.
     * @param description A brief description of the malfunction.
     * @param dateAndTime The date and time when the malfunction occurred.
     */
    public MalfunctionWrapper(String type, String vehicleID, String description, Date dateAndTime) {
        this.type = type;
        this.vehicleID = vehicleID;
        this.description = description;
        this.dateAndTime = dateAndTime;
    }

    /**
     * Returns the type of the vehicle.
     *
     * @return The vehicle type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type The new vehicle type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the vehicle ID.
     *
     * @return The vehicle ID.
     */
    public String getVehicleID() {
        return vehicleID;
    }

    /**
     * Sets the vehicle ID.
     *
     * @param vehicleID The new vehicle ID to set.
     */
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    /**
     * Returns the description of the malfunction.
     *
     * @return The malfunction description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the malfunction.
     *
     * @param description The new description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date and time of the malfunction.
     *
     * @return The date and time of the malfunction.
     */
    public Date getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Sets the date and time of the malfunction.
     *
     * @param dateAndTime The new date and time to set.
     */
    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
