package net.etf.project.financial;

import net.etf.project.model.rental.Rental;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;
import net.etf.project.model.vehicles.Vehicle;
import net.etf.project.gui.MainApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * The Receipt class generates and stores financial information for a specific vehicle rental.
 * It calculates the rental price, applies discounts or promotions, and generates a receipt file.
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Receipt
{
    /**
     * The rental associated with this receipt.
     */
    private Rental rental;

    /**
     * The total price of the rental.
     */
    private double totalPrice;

    /**
     * The discount applied to the rental, if any.
     */
    private double discount;

    /**
     * The promotion applied to the rental, if any.
     */
    private double promotion;

    /**
     * Indicates whether the rental took place in a wide area.
     */
    private boolean inWideArea;

    /**
     * Constructs a Receipt object for a given rental and calculates the total price.
     *
     * @param rental The rental for which this receipt is generated.
     */
    public Receipt(Rental rental)
    {
        this.rental = rental;
        this.inWideArea = rental.isInWideArea();
        this.totalPrice = calculateRentalPrice(rental);
    }

    /**
     * Generates a text file representing the receipt with details about the rental, total price,
     * any discounts or promotions, and the area (wide or narrow) of the ride.
     */
    public void generateReceipt()
    {
        String formattedDateTime = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(rental.getRentalDateTime());
        String filePath = MainApplication.RECEIPT_FOLDER_PATH  + File.separator + "receipt_" + rental.getRentalVehicleId()+ "_" + formattedDateTime + ".txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            writer.write("Receipt for rental:\n");
            writer.write(rental.toString() + "\n");
            writer.write("Total price: " + totalPrice + "\n");
            if(inWideArea)
            {
                writer.write("Ride area: Wide area\n");
            }
            else
            {
                writer.write("Ride area: Narrow area\n");
            }
            if(rental.isHasDiscount())
            {
                writer.write("Discount applied : " + discount + " ( " +  (MainApplication.DISCOUNT * 100) + " % )\n");
            }
            if(rental.isHasPromotion())
            {
                writer.write("Promotion applied : " + promotion + " ( " + (MainApplication.DISCOUNT_PROM * 100) + " % )\n");
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the total price for the rental, including discounts, promotions, and area factors.
     *
     * @param rental The rental for which the price is calculated.
     * @return The final rental price after applying any applicable discounts and promotions.
     */
    public double calculateRentalPrice(Rental rental)
    {
        Vehicle vehicle = rental.findVehicleById();
        if(vehicle == null)
        {
            throw new IllegalArgumentException("Vehicle with ID: " + rental.getRentalVehicleId() + " is not found.");
        }
        double unitPrice;

        if(vehicle instanceof Car)
        {
            unitPrice = MainApplication.CAR_UNIT_PRICE;
        } else if (vehicle instanceof Bicycle)
        {
            unitPrice = MainApplication.BIKE_UNIT_PRICE;
        } else if (vehicle instanceof Scooter)
        {
            unitPrice = MainApplication.SCOOTER_UNIT_PRICE;
        } else
        {
            throw new IllegalArgumentException("Unknown vehicle type: " + vehicle.getClass());
        }

        double basePrice = unitPrice * rental.getRentalDuration();

        double distanceFactor = rental.isInWideArea() ? MainApplication.DISTANCE_WIDE : MainApplication.DISTANCE_NARROW;

        double finalPrice = basePrice * distanceFactor;

        if(rental.isHasMalfunction())
            return 0;

        if(rental.isTenthRental())
        {
            discount = finalPrice * MainApplication.DISCOUNT;
            finalPrice -= discount;
        }

        if(rental.isHasPromotion())
        {
            promotion = finalPrice * MainApplication.DISCOUNT_PROM;
            finalPrice -= promotion;
        }
        return finalPrice;
    }

    /**
     * Returns the rental associated with this receipt.
     *
     * @return The rental object.
     */
    public Rental getRental() {
        return rental;
    }

    /**
     * Sets the rental associated with this receipt.
     *
     * @param rental The rental object to be set.
     */
    public void setRental(Rental rental) {
        this.rental = rental;
    }

    /**
     * Returns the total price of the rental.
     *
     * @return The total price.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the rental.
     *
     * @param totalPrice The total price to be set.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Returns the discount applied to the rental.
     *
     * @return The discount amount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount amount for the rental.
     *
     * @param discount The discount amount to be set.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Returns the promotion applied to the rental.
     *
     * @return The promotion amount.
     */
    public double getPromotion() {
        return promotion;
    }

    /**
     * Sets the promotion amount for the rental.
     *
     * @param promotion The promotion amount to be set.
     */
    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    /**
     * Returns whether the rental occurred in the wide area.
     *
     * @return true if the rental occurred in the wide area, false otherwise.
     */
    public boolean isInWideArea() {
        return inWideArea;
    }

    /**
     * Sets whether the rental occurred in the wide area.
     *
     * @param inWideArea true if the rental occurred in the wide area, false otherwise.
     */
    public void setInWideArea(boolean inWideArea) {
        this.inWideArea = inWideArea;
    }
}
