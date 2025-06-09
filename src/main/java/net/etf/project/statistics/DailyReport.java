package net.etf.project.statistics;

import net.etf.project.financial.Receipt;
import net.etf.project.gui.MainApplication;
import net.etf.project.model.rental.Rental;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;
import net.etf.project.model.vehicles.Vehicle;
import net.etf.project.gui.MainApplication;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import static net.etf.project.gui.MainApplication.receipts;

/**
 * The {@code DailyReport} class represents a summary of financial and operational metrics
 * for a particular day. It aggregates data from all the receipts for that day and calculates
 * important statistics like income, discounts, promotions, repair costs, and more.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class DailyReport
{
    /**
     * The date for which the report is generated, in the format 'dd.MM.yyyy'.
     */
    private String date;
    /**
     * The total income generated from vehicle rentals on the specified date.
     */
    private double dailyIncome;
    /**
     * The total amount of discounts applied on rentals for the specified date.
     */
    private double dailyDiscounts;
    /**
     * The total amount of promotions applied on rentals for the specified date.
     */
    private double dailyPromotions;
    /**
     * The total income generated from rentals in wide areas on the specified date.
     */
    private double dailyWideAreaIncome;
    /**
     * The total income generated from rentals in narrow areas on the specified date.
     */
    private double dailyNarrowAreaIncome;
    /**
     * The total maintenance costs on the specified date.
     */
    private double dailyMaintenanceCost;
    /**
     * The total repair costs on the specified date.
     */
    private double dailyRepairCosts;

    /**
     * Constructs a {@code DailyReport} object for a given date.
     *
     * @param date the date of the report
     * @param receipts the list of receipts used to calculate the daily metrics
     */
    public DailyReport(String date, List<Receipt> receipts)
    {
        this.date = date;
        calculateMetrics(receipts);
    }

    /**
     * Calculates the daily metrics such as income, discounts, promotions, wide and narrow area income,
     * maintenance costs, and repair costs.
     *
     * @param receipts the list of receipts from which to calculate the metrics
     */
    public void calculateMetrics(List<Receipt> receipts)
    {
        dailyIncome = receipts.stream().mapToDouble(Receipt::getTotalPrice).sum();
        dailyDiscounts = receipts.stream().mapToDouble(Receipt::getDiscount).sum();
        dailyPromotions = receipts.stream().mapToDouble(Receipt::getPromotion).sum();
        dailyWideAreaIncome = receipts.stream().filter(Receipt::isInWideArea).mapToDouble(Receipt::getTotalPrice).sum();
        dailyNarrowAreaIncome = receipts.stream().filter(r -> !r.isInWideArea()).mapToDouble(Receipt::getTotalPrice).sum();
        dailyMaintenanceCost = dailyIncome * MainApplication.MAINTENANCE_COEFFICIENT;
        dailyRepairCosts = receipts.stream()
                .mapToDouble(receipt -> {
                    Rental rental = receipt.getRental();
                    Vehicle vehicle = rental.findVehicleById();
                    if (rental.isHasMalfunction()) {
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
        printDailyReport();
    }

    /**
     * Prints the daily report to the console.
     * The report includes the income, discounts, promotions, area-based income, and repair costs.
     */
    public void printDailyReport()
    {
        System.out.println("Date: " + date + "\n");
        System.out.println("Income : " + dailyIncome + "\n");
        System.out.println("Discount: " + dailyDiscounts + "\n");
        System.out.println("Promotion: " + dailyPromotions + "\n");
        System.out.println("In wide area: " + dailyWideAreaIncome + "\n");
        System.out.println("In narrow area: " + dailyNarrowAreaIncome + "\n");
        System.out.println("Repair costs: " + dailyRepairCosts + "\n");
        System.out.println("-----------------------------------------------------");
    }

    /**
     * Gets the total daily discounts.
     *
     * @return the daily discounts
     */
    public double getDailyDiscounts() {
        return dailyDiscounts;
    }

    /**
     * Sets the total daily discounts.
     *
     * @param dailyDiscounts the daily discounts to set
     */
    public void setDailyDiscounts(double dailyDiscounts) {
        this.dailyDiscounts = dailyDiscounts;
    }

    /**
     * Gets the total daily income.
     *
     * @return the daily income
     */
    public double getDailyIncome() {
        return dailyIncome;
    }

    /**
     * Sets the total daily income.
     *
     * @param dailyIncome the daily income to set
     */
    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    /**
     * Gets the daily maintenance cost, calculated as a fraction of the daily income.
     *
     * @return the daily maintenance cost
     */
    public double getDailyMaintenanceCost() {
        return dailyMaintenanceCost;
    }

    /**
     * Sets the daily maintenance cost.
     *
     * @param dailyMaintenanceCost the maintenance cost to set
     */
    public void setDailyMaintenanceCost(double dailyMaintenanceCost) {
        this.dailyMaintenanceCost = dailyMaintenanceCost;
    }

    /**
     * Gets the daily income from rentals in the narrow area of the city.
     *
     * @return the narrow area income
     */
    public double getDailyNarrowAreaIncome() {
        return dailyNarrowAreaIncome;
    }

    /**
     * Sets the daily income from rentals in the narrow area of the city.
     *
     * @param dailyNarrowAreaIncome the narrow area income to set
     */
    public void setDailyNarrowAreaIncome(double dailyNarrowAreaIncome) {
        this.dailyNarrowAreaIncome = dailyNarrowAreaIncome;
    }

    /**
     * Gets the total promotions applied during the day.
     *
     * @return the daily promotions
     */
    public double getDailyPromotions() {
        return dailyPromotions;
    }

    /**
     * Sets the total promotions applied during the day.
     *
     * @param dailyPromotions the promotions to set
     */
    public void setDailyPromotions(double dailyPromotions) {
        this.dailyPromotions = dailyPromotions;
    }

    /**
     * Gets the total daily repair costs.
     *
     * @return the daily repair costs
     */
    public double getDailyRepairCosts() {
        return dailyRepairCosts;
    }

    /**
     * Sets the total daily repair costs.
     *
     * @param dailyRepairCosts the repair costs to set
     */
    public void setDailyRepairCosts(double dailyRepairCosts) {
        this.dailyRepairCosts = dailyRepairCosts;
    }

    /**
     * Gets the total income from rentals in the wide area of the city.
     *
     * @return the wide area income
     */
    public double getDailyWideAreaIncome() {
        return dailyWideAreaIncome;
    }

    /**
     * Sets the total income from rentals in the wide area of the city.
     *
     * @param dailyWideAreaIncome the wide area income to set
     */
    public void setDailyWideAreaIncome(double dailyWideAreaIncome) {
        this.dailyWideAreaIncome = dailyWideAreaIncome;
    }

    /**
     * Gets the date for which this daily report is generated.
     *
     * @return the date of the report
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for which this daily report is generated.
     *
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
}
