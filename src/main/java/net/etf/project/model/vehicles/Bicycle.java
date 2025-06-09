package net.etf.project.model.vehicles;

import net.etf.project.gui.MainApplication;

/**
 * Represents a bicycle in the vehicle rental system.
 * A bicycle has a specific range and is a subclass of {@link Vehicle}.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Bicycle extends Vehicle
{

    /** The maximum range that the bicycle can travel on a full charge. */
    private int range;

    /**
     * Constructs a new Bicycle object with the specified parameters.
     *
     * @param vehicleID The unique identifier of the bicycle.
     * @param purchasePrice The price at which the bicycle was purchased.
     * @param manufacturer The manufacturer of the bicycle.
     * @param model The model of the bicycle.
     * @param range The maximum range the bicycle can travel.
     */
    public Bicycle(String vehicleID, double purchasePrice, String manufacturer, String model, int range) {
        super(vehicleID,purchasePrice,manufacturer,model);
        this.range = range;
    }

    /**
     * Returns the range of the bicycle.
     *
     * @return The range (in kilometers) of the bicycle.
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of the bicycle.
     *
     * @param range The new range (in kilometers) to set.
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Calculates the repair cost for the bicycle based on the number of malfunctions and a specific coefficient.
     * The repair cost is calculated using the purchase price of the bicycle,
     * the number of malfunctions, and the coefficient defined for bicycles.
     *
     * @return The calculated repair cost for the bicycle.
     */
    @Override
    public double calculateRepairCost() {
        double coefficient = MainApplication.BICYCLE_COEFFICIENT;
        int malfunctionCount = malfunctions.size();
        if(malfunctionCount == 0)
            repairCosts = 0.0;
        else
            repairCosts = getPurchasePrice() * malfunctionCount * coefficient;
        return repairCosts;
    }
}