package net.etf.project.model.vehicles;

import net.etf.project.gui.MainApplication;

/**
 * Represents a scooter in the vehicle system. Extends the {@link Vehicle} class
 * and includes additional attribute maximal speed.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Scooter extends Vehicle
{

    /** The maximum speed of the scooter. */
    private int maxSpeed;

    /**
     * Constructs a new Scooter with the specified attributes.
     *
     * @param vehicleID     The unique identifier for the scooter.
     * @param purchasePrice The purchase price of the scooter.
     * @param manufacturer   The manufacturer of the scooter.
     * @param model         The model of the scooter.
     * @param maxSpeed      The maximum speed of the scooter.
     */
    public Scooter(String vehicleID, double purchasePrice, String manufacturer, String model, int maxSpeed) {
        super(vehicleID,purchasePrice,manufacturer,model);
        this.maxSpeed = maxSpeed;
    }

    /**
     * Returns the maximum speed of the scooter.
     *
     * @return The maximum speed of the scooter.
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets the maximum speed of the scooter.
     *
     * @param maxSpeed The new maximum speed to set.
     */
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Calculates the repair cost for the scooter based on the number of malfunctions and a specific coefficient.
     *
     * The repair cost is calculated using the purchase price of the scooter,
     * the number of malfunctions, and the coefficient defined for scooter.
     *
     * @return The calculated repair cost for the scooter.
     */
    @Override
    public double calculateRepairCost() {
        double coefficient = MainApplication.SCOOTER_COEFFICIENT;
        int malfunctionCount = malfunctions.size();
        if(malfunctionCount == 0)
            repairCosts = 0.0;
        else
            repairCosts = getPurchasePrice() * malfunctionCount * coefficient;
        return repairCosts;
    }
}