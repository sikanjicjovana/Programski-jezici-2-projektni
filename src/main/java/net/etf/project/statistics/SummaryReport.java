package net.etf.project.statistics;

import net.etf.project.financial.Receipt;
import net.etf.project.gui.MainApplication;
import net.etf.project.model.rental.Rental;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;
import net.etf.project.model.vehicles.Vehicle;

import java.util.*;

import static net.etf.project.gui.MainApplication.receipts;

/**
 * The {@code SummaryReport} class is responsible for generating and printing
 * a summary report of the rental receipts. It calculates various financial
 * metrics such as total income, discounts, promotions, and costs related to
 * the maintenance and repair of vehicles.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class SummaryReport
{
    /**
     * The total income generated from vehicle rentals.
     */
    private double totalIncome;
    /**
     * The total amount of discounts applied to rentals.
     */
    private double totalDiscounts;
    /**
     * The total amount of promotions applied to rentals.
     */
    private double totalPromotions;
    /**
     * The total income generated from rentals in wide areas.
     */
    private double totalWideAreaIncome;
    /**
     * The total income generated from rentals in narrow areas.
     */
    private double totalNarrowAreaIncome;
    /**
     * The total maintenance costs.
     */
    private double totalMaintenanceCost;
    /**
     * The total repair costs.
     */
    private double totalRepairCost;
    /**
     * The total company costs.
     */
    private double totalCompanyCosts;
    /**
     * The total tax collected from vehicle rentals.
     */
    private double totalTax;

    /**
     * Generates the summary report by calculating the total income, discounts,
     * promotions, maintenance costs, repair costs, and other metrics using
     * the list of {@link Receipt} objects stored in the application.
     *
     */
    public void generateSummaryReport()
    {
        totalIncome = receipts.stream().mapToDouble(Receipt::getTotalPrice).sum();
        totalDiscounts = receipts.stream().mapToDouble(Receipt::getDiscount).sum();
        totalPromotions = receipts.stream().mapToDouble(Receipt::getPromotion).sum();

        totalWideAreaIncome = receipts.stream()
                .filter(Receipt::isInWideArea)
                .mapToDouble(Receipt::getTotalPrice)
                .sum();
        totalNarrowAreaIncome = receipts.stream()
                .filter(receipt -> !receipt.isInWideArea())
                .mapToDouble(Receipt::getTotalPrice)
                .sum();

        totalMaintenanceCost = totalIncome * MainApplication.MAINTENANCE_COEFFICIENT;

        totalRepairCost = receipts.stream()
                .mapToDouble(receipt -> {
                    Rental rental = receipt.getRental();
                    Vehicle vehicle = rental.findVehicleById();
                    if(rental.isHasMalfunction()) {
                        if (vehicle instanceof Car) {
                            return MainApplication.CAR_COEFFICIENT * vehicle.getPurchasePrice();
                        } else if (vehicle instanceof Bicycle) {
                            return MainApplication.BICYCLE_COEFFICIENT * vehicle.getPurchasePrice();
                        } else if (vehicle instanceof Scooter) {
                            return MainApplication.SCOOTER_COEFFICIENT * vehicle.getPurchasePrice();
                        }
                    }
                    return 0;
                }).sum();

        totalCompanyCosts = totalIncome * MainApplication.COMPANY_COSTS_COEFFICIENT;

        totalTax = (totalIncome - totalMaintenanceCost - totalRepairCost - totalCompanyCosts) * MainApplication.TAX_COEFFICIENT;
    }

    /**
     * Prints the summary report to the console, displaying various calculated metrics.
     */
    public void printSummaryReport()
    {
        System.out.println("Summary report:\n");
        System.out.println("1. Total income: " + totalIncome + "\n");
        System.out.println("2. Total discounts: " + totalDiscounts + "\n");
        System.out.println("3. Total promotions: " + totalPromotions + "\n");
        System.out.println("4. Total wide area income: " + totalWideAreaIncome + "\n");
        System.out.println("5. Total narrow area income: " + totalNarrowAreaIncome + "\n");
        System.out.println("6. Total maintenance cost: " + totalMaintenanceCost + "\n");
        System.out.println("7. Total repair cost: " + totalRepairCost + "\n");
        System.out.println("8. Total company costs: " + totalCompanyCosts + "\n");
        System.out.println("9. Total tax: " + totalTax + "\n");
    }

    /**
     * Gets the total company costs.
     *
     * @return the total company costs
     */
    public double getTotalCompanyCosts() {
        return totalCompanyCosts;
    }

    /**
     * Sets the total company costs.
     *
     * @param totalCompanyCosts the total company costs to set
     */
    public void setTotalCompanyCosts(double totalCompanyCosts) {
        this.totalCompanyCosts = totalCompanyCosts;
    }

    /**
     * Gets the total discounts.
     *
     * @return the total discounts
     */
    public double getTotalDiscounts() {
        return totalDiscounts;
    }

    /**
     * Sets the total discounts.
     *
     * @param totalDiscounts the total discounts to set
     */
    public void setTotalDiscounts(double totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    /**
     * Gets the total income.
     *
     * @return the total income
     */
    public double getTotalIncome() {
        return totalIncome;
    }

    /**
     * Sets the total income.
     *
     * @param totalIncome the total income to set
     */
    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    /**
     * Gets the total maintenance cost.
     *
     * @return the total maintenance cost
     */
    public double getTotalMaintenanceCost() {
        return totalMaintenanceCost;
    }

    /**
     * Sets the total maintenance cost.
     *
     * @param totalMaintenanceCost the total maintenance cost to set
     */
    public void setTotalMaintenanceCost(double totalMaintenanceCost) {
        this.totalMaintenanceCost = totalMaintenanceCost;
    }

    /**
     * Gets the total income from rentals in narrow areas.
     *
     * @return the total narrow area income
     */
    public double getTotalNarrowAreaIncome() {
        return totalNarrowAreaIncome;
    }

    /**
     * Sets the total income from rentals in narrow areas.
     *
     * @param totalNarrowAreaIncome the total narrow area income to set
     */
    public void setTotalNarrowAreaIncome(double totalNarrowAreaIncome) {
        this.totalNarrowAreaIncome = totalNarrowAreaIncome;
    }

    /**
     * Gets the total promotions.
     *
     * @return the total promotions
     */
    public double getTotalPromotions() {
        return totalPromotions;
    }

    /**
     * Sets the total promotions.
     *
     * @param totalPromotions the total promotions to set
     */
    public void setTotalPromotions(double totalPromotions) {
        this.totalPromotions = totalPromotions;
    }

    /**
     * Gets the total repair cost for malfunctioning vehicles.
     *
     * @return the total repair cost
     */
    public double getTotalRepairCost() {
        return totalRepairCost;
    }

    /**
     * Sets the total repair cost.
     *
     * @param totalRepairCost the total repair cost to set
     */
    public void setTotalRepairCost(double totalRepairCost) {
        this.totalRepairCost = totalRepairCost;
    }

    /**
     * Gets the total tax.
     *
     * @return the total tax
     */
    public double getTotalTax() {
        return totalTax;
    }

    /**
     * Sets the total tax.
     *
     * @param totalTax the total tax to set
     */
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * Gets the total income from rentals in wide areas.
     *
     * @return the total wide area income
     */
    public double getTotalWideAreaIncome() {
        return totalWideAreaIncome;
    }

    /**
     * Sets the total income from rentals in wide areas.
     *
     * @param totalWideAreaIncome the total wide area income to set
     */
    public void setTotalWideAreaIncome(double totalWideAreaIncome) {
        this.totalWideAreaIncome = totalWideAreaIncome;
    }
}
