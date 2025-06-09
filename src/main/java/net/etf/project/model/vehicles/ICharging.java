package net.etf.project.model.vehicles;

/**
 * This interface defines the contract for charging battery-powered vehicles.
 * Any class implementing this interface must provide an implementation
 * for the {@code batteryCharging()} method, which recharges the vehicle's battery.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public interface ICharging {

    /**
     * Recharges the vehicle's battery to full capacity.
     * Implementing classes should define the specific behavior
     * for how the battery is charged.
     */
    void batteryCharging();
}