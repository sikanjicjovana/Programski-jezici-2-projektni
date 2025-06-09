package net.etf.project.model.vehicles;
import java.io.Serializable;
import java.util.*;

/**
 * Represents an abstract base class for all vehicles in the system.
 * Implements {@link ICharging} for battery charging functionality
 * and {@link Serializable} for object serialization.
 * Each vehicle has a unique ID, purchase price, manufacturer, model,
 * battery level, a list of malfunctions, and repair costs.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public abstract class Vehicle implements ICharging, Serializable {

    /**
     * The minimum battery level required before the vehicle needs charging.
     */
    public static final int MIN_BATTERY_LEVEL = 20;

    /**
     * The unique identifier of the vehicle.
     */
    protected String vehicleID;

    /**
     * The purchase price of the vehicle.
     */
    protected double purchasePrice;

    /**
     * The manufacturer of the vehicle.
     */
    protected String manufacturer;

    /**
     * The model of the vehicle.
     */
    protected String model;

    /**
     * The current battery level of the vehicle.
     */
    protected int currentBatteryLevel;

    /**
     * The list of malfunctions that occurred with the vehicle.
     */
    protected List<Malfunction> malfunctions;

    /**
     * The total repair costs associated with the vehicle.
     */
    protected double repairCosts;

    /**
     * Constructs a new {@code Vehicle} object with the specified parameters.
     * Sets the initial battery level to 100 and initializes the malfunctions list.
     *
     * @param vehicleID      the unique identifier of the vehicle
     * @param purchasePrice  the price at which the vehicle was purchased
     * @param manufacturer   the manufacturer of the vehicle
     * @param model          the model of the vehicle
     */
    public Vehicle(String vehicleID, double purchasePrice, String manufacturer, String model) {
        this.vehicleID = vehicleID;
        this.purchasePrice = purchasePrice;
        this.manufacturer = manufacturer;
        this.model = model;
        this.currentBatteryLevel = 100;
        this.malfunctions = new ArrayList<>();
        this.repairCosts = 0.0;
    }

    /**
     * Returns the unique identifier of the vehicle.
     *
     * @return the vehicle ID
     */
    public String getVehicleID() {
        return vehicleID;
    }

    /**
     * Sets a new unique identifier for the vehicle.
     *
     * @param vehicleID the new vehicle ID to set
     */
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    /**
     * Returns the purchase price of the vehicle.
     *
     * @return the purchase price of the vehicle
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets a new purchase price for the vehicle.
     *
     * @param purchasePrice the new purchase price to set
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Returns the manufacturer of the vehicle.
     *
     * @return the vehicle's manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets a new manufacturer for the vehicle.
     *
     * @param manufacturer the new manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Returns the model of the vehicle.
     *
     * @return the vehicle's model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets a new model for the vehicle.
     *
     * @param model the new model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the current battery level of the vehicle.
     *
     * @return the current battery level
     */
    public int getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    /**
     * Sets the current battery level for the vehicle.
     *
     * @param currentBatteryLevel the new battery level to set
     */
    public void setCurrentBatteryLevel(int currentBatteryLevel) {
        this.currentBatteryLevel = currentBatteryLevel;
    }

    /**
     * Returns the list of malfunctions that occurred with the vehicle.
     *
     * @return the list of malfunctions
     */
    public List<Malfunction> getMalfunctions() {
        return malfunctions;
    }

    /**
     * Sets the list of malfunctions that occured with the vehicle.
     *
     * @param malfunctions the new list of malfunctions to set
     */
    public void setMalfunctions(List<Malfunction> malfunctions) {
        this.malfunctions = malfunctions;
    }

    /**
     * Returns the total repair costs associated with the vehicle.
     *
     * @return the repair costs
     */
    public double getRepairCosts() {
        return repairCosts;
    }

    /**
     * Sets the total repair costs for the vehicle.
     *
     * @param repairCosts the new repair costs to set
     */
    public void setRepairCosts(double repairCosts) {
        this.repairCosts = repairCosts;
    }

    /**
     * Adds a new malfunction to the vehicle's malfunction list
     * and updates the repair costs.
     *
     * @param malfunctionDateTime the date and time when the malfunction occurred
     */
    public void addMalfunction(Date malfunctionDateTime)
    {
        malfunctions.add(new Malfunction(malfunctionDateTime));
        calculateRepairCost();
    }

    /**
     * Calculates the repair cost for the vehicle. This method must be implemented
     * by subclasses as the calculation will depend on the specific type of vehicle.
     *
     * @return the calculated repair cost
     */
    public abstract double calculateRepairCost();

    /**
     * Checks if two vehicles are equal by comparing their vehicle IDs.
     *
     * @param o the object to compare with
     * @return {@code true} if the vehicle IDs are the same, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleID, vehicle.vehicleID);
    }

    /**
     * Returns the hash code for the vehicle, based on the vehicle ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(vehicleID);
    }

    /**
     * Returns a string representation of the vehicle, including its ID, price,
     * manufacturer, model, battery level, and repair costs.
     *
     * @return a string representation of the vehicle
     */
    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName() + " ID: " + vehicleID + " price: " + purchasePrice + " manufacturer: " + manufacturer + " model: " + model + " battery level: " + currentBatteryLevel + " repair costs: " + repairCosts);
        return sb.toString();
    }

    /**
     * Charges the vehicle battery to full capacity (100%).
     */
    @Override
    public void batteryCharging()
    {
        this.currentBatteryLevel = 100;
    }

    /**
     * Reduces the vehicle's battery level by 5 units. Ensures the battery level does not drop below 0.
     */
    public void reduceBattery()
    {
        currentBatteryLevel -= 5;
        if(currentBatteryLevel < 0) currentBatteryLevel = 0;
    }
}