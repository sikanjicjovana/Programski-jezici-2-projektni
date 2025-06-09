package net.etf.project.model.vehicles;
import net.etf.project.gui.MainApplication;

import java.util.Date;
import java.util.Random;

/**
 * Represents a car in the vehicle system. Extends the {@link Vehicle} class
 * and includes additional attributes such as the purchase date, description,
 * and whether the car is a multi-passenger vehicle.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Car extends Vehicle
{
    /**
     * The date when the car was purchased.
     */
    private Date purchaseDate;

    /**
     * A description of the car, such as specific features or details.
     */
    private String description;

    /**
     * Indicates whether the car supports multiple passengers.
     */
    private boolean multiPassenger;

    /**
     * Constructs a new {@code Car} object with the specified parameters.
     *
     * @param vehicleID      the unique identifier of the car
     * @param purchasePrice  the price at which the car was purchased
     * @param manufacturer   the manufacturer of the car
     * @param model          the model of the car
     * @param purchaseDate   the date the car was purchased
     * @param description    a description of the car
     */
    public Car(String vehicleID, double purchasePrice, String manufacturer, String model, Date purchaseDate, String description) {
        super(vehicleID,purchasePrice,manufacturer,model);
        this.purchaseDate = purchaseDate;
        this.description = description;

        Random rand = new Random();
        this.multiPassenger = rand.nextBoolean();
    }

    /**
     * Returns the date when the car was purchased.
     *
     * @return the purchase date of the car
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the purchase date of the car.
     *
     * @param purchaseDate the new purchase date to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Returns the description of the car.
     *
     * @return the car's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new description for the car.
     *
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks whether the car supports multiple passengers.
     *
     * @return {@code true} if the car is a multi-passenger vehicle, {@code false} otherwise
     */
    public boolean isMultiPassenger() {
        return multiPassenger;
    }

    /**
     * Sets whether the car supports multiple passengers.
     *
     * @param multiPassenger {@code true} if the car is a multi-passenger vehicle, {@code false} otherwise
     */
    public void setMultiPassenger(boolean multiPassenger) {
        this.multiPassenger = multiPassenger;
    }

    /**
     * Calculates the repair cost for the car based on the number of malfunctions
     * and the car's purchase price. The repair cost is determined using a specific
     * coefficient and the number of malfunctions.
     *
     * @return the calculated repair cost
     */
    @Override
    public double calculateRepairCost() {
        double coefficient = MainApplication.CAR_COEFFICIENT;
        int malfunctionCount = malfunctions.size();
        if(malfunctionCount == 0)
            repairCosts = 0.0;
        else
            repairCosts = getPurchasePrice() * malfunctionCount * coefficient;
        return repairCosts;
    }
}