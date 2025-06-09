package net.etf.project.model.rental;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import net.etf.project.financial.Receipt;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Vehicle;
import net.etf.project.gui.MainApplication;
import net.etf.project.simulation.CityMap;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a rental transaction for a vehicle, tracking details such as the rental date, user information,
 * vehicle ID, and the movement of the vehicle from start to end location on a simulated map.
 * Implements {@link Runnable} to allow vehicle movement simulation in a separate thread.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Rental implements Runnable
{
    /**
     * Date and time of the rental.
     **/
    private Date rentalDateTime;
    /**
     * Name of the user renting the vehicle.
     **/
    private String userName;
    /**
     * ID of the rented vehicle.
     **/
    private String rentalVehicleId;
    /**
     * Starting location of the vehicle.
     **/
    private Location startLocation;
    /**
     * Ending location of the vehicle.
     **/
    private Location endLocation;
    /**
     * Duration of the rental in seconds.
     **/
    private double rentalDuration;
    /**
     * Additional identification document for users that rent a car.
     **/
    private final String identificationDocument;
    /**
     * Driving licence for users that rent a car.
     **/
    private int drivingLicenseNumber;
    /**
     * Indicates if the vehicle has a malfunction.
     **/
    private boolean hasMalfunction;
    /**
     * Indicates if the rental has a promotion applied.
     **/
    private boolean hasPromotion;
    /**
     * Indicates if the vehicle has a discount.
     **/
    private boolean hasDiscount;

    /**
     * Constructs a new Rental object with all necessary details such as the rental date, user information,
     * vehicle ID, start and end locations, rental duration, and malfunction status.
     *
     * @param rentalDateTime     Date and time of the rental
     * @param userName           Name of the user renting the vehicle
     * @param rentalVehicleId    ID of the rented vehicle
     * @param startLocation      Starting location of the vehicle
     * @param endLocation        Ending location of the vehicle
     * @param rentalDuration     Duration of the rental in seconds
     * @param hasMalfunction     Indicates if the vehicle has a malfunction
     * @param hasPromotion       Indicates if the rental has a promotion applied
     */
    public Rental(Date rentalDateTime, String userName, String rentalVehicleId, Location startLocation, Location endLocation, double rentalDuration, boolean hasMalfunction, boolean hasPromotion){
       this.rentalDateTime = rentalDateTime;
       this.userName = userName;
       this.rentalVehicleId = rentalVehicleId;
       this.startLocation = startLocation;
       this.endLocation = endLocation;
       this.rentalDuration = rentalDuration;
       this.hasMalfunction = hasMalfunction;
       this.hasPromotion = hasPromotion;
       this.hasDiscount = false;

       Random random = new Random();
       boolean isForeigner = random.nextBoolean();

       //In case that the type of vehicle is car additional attributes are generated, if not default values are set

       Optional<Vehicle> vehicle = MainApplication.vehicles.stream().filter(v -> v.getVehicleID().equals(rentalVehicleId) && v instanceof Car).findFirst();

       if(vehicle.isPresent())
       {
           if(isForeigner)
           {
               this.identificationDocument = "Passport";
           }
           else
           {
              this.identificationDocument = "ID Card";
           }
           this.drivingLicenseNumber = random.nextInt(999999);
       }
       else
       {
           this.identificationDocument = " / ";
           this.drivingLicenseNumber = -1;
       }
    }

    /**
     * Finds a vehicle by its ID from the list of vehicles.
     *
     * @return The vehicle object with the matching ID, or null if not found.
     */
    public Vehicle findVehicleById()
    {
        return MainApplication.vehicles.stream().filter(v -> v.getVehicleID().equals(rentalVehicleId)).findFirst().orElse(null);
    }

    /**
     * Checks if the vehicle enters a wide area, which is defined by certain coordinate ranges, during the rental.
     *
     * @return True if the vehicle enters a wide area, false otherwise.
     */
    public boolean isInWideArea()
    {
        int startX = startLocation.getX();
        int startY = startLocation.getY();
        int endX = endLocation.getX();
        int endY = endLocation.getY();

        // Traverse Y-axis between start and end
        int currentY = startY;
        while(currentY != endY)
        {
            if(isWideCoordinate(startX, currentY))
            {
                return true;
            }
            if(endY > startY)
            {
                currentY++;
            }
            else
            {
                currentY--;
            }
        }
        // Traverse X-axis between start and end
        int currentX = startX;
        while(currentX != endX)
        {
            if(isWideCoordinate(currentX,endY))
            {
                return true;
            }
            if(endX > startX)
            {
                currentX++;
            }
            else
            {
                currentX--;
            }
        }
        return isWideCoordinate(endX, endY);
    }

    /**
     * Checks if the given coordinate falls within a wide area.
     *
     * @param x X-coordinate to check
     * @param y Y-coordinate to check
     * @return True if the coordinate is in the wide area, false otherwise.
     */
    private boolean isWideCoordinate(int x, int y)
    {
        return ( x >= 0 && x <= 4 || x >= 15 && x <= 19) || (y >= 0 && y <= 4 || y >= 15 && y <= 19);
    }

    /**
     * Checks if the current rental is the user's tenth rental.
     *
     * @return True if the user is renting for the tenth time, false otherwise.
     */
    public boolean isTenthRental()
    {
        Integer rentalCount = MainApplication.userRentals.get(userName);
        System.out.println("Rental check for user: " + userName + " - Number of rentals: " + rentalCount);
        return rentalCount != null && rentalCount % 10 == 0;
    }

    /**
     * Gets the rental date and time.
     *
     * @return The rental date and time.
     */
    public Date getRentalDateTime() {
        return rentalDateTime;
    }

    /**
     * Sets the rental date and time.
     *
     * @param rentalDateTime The rental date and time to set.
     */
    public void setRentalDateTime(Date rentalDateTime) {
        this.rentalDateTime = rentalDateTime;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     *
     * @param userName The username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the rental vehicle ID.
     *
     * @return The rental vehicle ID.
     */
    public String getRentalVehicleId() {
        return rentalVehicleId;
    }

    /**
     * Sets the rental vehicle ID.
     *
     * @param rentalVehicleId The rental vehicle ID to set.
     */
    public void setRentalVehicleId(String rentalVehicleId) {
        this.rentalVehicleId = rentalVehicleId;
    }

    /**
     * Gets the start location of the rental.
     *
     * @return The start location.
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * Sets the start location of the rental.
     *
     * @param startLocation The start location to set.
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Gets the end location of the rental.
     *
     * @return The end location.
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * Sets the end location of the rental.
     *
     * @param endLocation The end location to set.
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * Gets the rental duration.
     *
     * @return The rental duration in seconds.
     */
    public double getRentalDuration() {
        return rentalDuration;
    }

    /**
     * Sets the rental duration.
     *
     * @param rentalDuration The rental duration in seconds to set.
     */
    public void setRentalDuration(double rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    /**
     * Gets the identification document used for the rental.
     *
     * @return The identification document.
     */
    public String getIdentificationDocument() {
        return identificationDocument;
    }

    /**
     * Gets the driving license number for the rental.
     *
     * @return The driving license number.
     */
    public int getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    /**
     * Sets the driving license number for the rental.
     *
     * @param drivingLicenseNumber The driving license number to set.
     */
    public void setDrivingLicenseNumber(int drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    /**
     * Checks if the vehicle has a malfunction during the rental.
     *
     * @return `true` if there is a malfunction, `false` otherwise.
     */
    public boolean isHasMalfunction() {
        return hasMalfunction;
    }

    /**
     * Sets whether the vehicle has a malfunction during the rental.
     *
     * @param hasMalfunction The malfunction status to set.
     */
    public void setHasMalfunction(boolean hasMalfunction) {
        this.hasMalfunction = hasMalfunction;
    }

    /**
     * Checks if the rental has a promotion applied.
     *
     * @return `true` if there is a promotion, `false` otherwise.
     */
    public boolean isHasPromotion() {
        return hasPromotion;
    }

    /**
     * Sets whether the rental has a promotion applied.
     *
     * @param hasPromotion The promotion status to set.
     */
    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    /**
     * Checks if the rental has a discount applied.
     *
     * @return `true` if there is a discount, `false` otherwise.
     */
    public boolean isHasDiscount() {
        return hasDiscount;
    }

    /**
     * Sets whether the rental has a discount applied.
     *
     * @param hasDiscount The discount status to set.
     */
    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    /**
     * Starts the vehicle simulation in a separate thread, handling movement from the start to end location
     * while updating the map in real time.<br>
     *
     * The method performs the following steps:<br>
     * 1. Finds the vehicle by its ID and places it on the map at the starting position.<br>
     * 2. Calculates the steps required to reach the destination and pauses between steps to simulate real-time movement.<br>
     * 3. Updates the vehicle's position on the map step by step, using JavaFX's `Platform.runLater()` to ensure the GUI is updated correctly.<br>
     * 4. If a malfunction occurs, the vehicle moves only three steps before stopping.<br>
     * 5. Monitors battery level during movement and recharges if necessary.<br>
     * 6. Upon reaching the destination, the vehicle is removed from the map and the rental is marked as complete.<br>
     * 7. Generates a receipt and updates discount eligibility based on the number of rentals.
     *
     * @see Platform#runLater(Runnable) Ensures that map updates occur on the correct thread.
     * @see Vehicle#reduceBattery() Reduces the vehicle's battery level after each step of movement.
     * @see MainApplication#simulationMap Used to interact with the GUI map for vehicle visualization.
     */
    @Override
    public void run() {
        Vehicle vehicle = findVehicleById();
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        int startX = startLocation.getX();
        int startY = startLocation.getY();
        int endX = endLocation.getX();
        int endY = endLocation.getY();
        int currentX = startX;
        int currentY = startY;

        int finalCurrentX = currentX;
        int finalCurrentY = currentY;
        Platform.runLater(() -> MainApplication.simulationMap.addVehicle(finalCurrentX, finalCurrentY, vehicle));
        //MainApplication.simulationMap.display();

        int steps = Math.abs(endX - startX) + Math.abs(endY - startY);
        long pauseDuration = (long) (rentalDuration * 1000 / steps);

        try {
            while (currentX != endX || currentY != endY) {
                if(hasMalfunction){
                    System.out.printf("Vehicle %s has a malfunction and will move only a few fields before stopping.%n", vehicle.getVehicleID());
                    for(int i = 0; i < 3 && (currentX != endX || currentY != endY); i++){

                        int finalCurrentX1 = currentX;
                        int finalCurrentY1 = currentY;

                        Platform.runLater(() -> MainApplication.simulationMap.removeVehicle(finalCurrentX1, finalCurrentY1, vehicle));
                        int prevX = currentX;
                        int prevY = currentY;

                        if (currentY != endY) {
                            if (endY > startY) currentY++;
                            else currentY--;
                        } else if (currentX != endX) {
                            if (endX > startX) currentX++;
                            else currentX--;
                        }

                        int finalNewX = currentX;
                        int finalNewY = currentY;
                        Platform.runLater(() -> MainApplication.simulationMap.addVehicle(finalNewX, finalNewY, vehicle));
                        reduceBatteryAndCheck(vehicle,currentX,currentY);
                        System.out.printf("Vehicle %s moved from (%d, %d) to (%d, %d)%n", vehicle.getVehicleID(), prevX, prevY, currentX, currentY);
                        TimeUnit.MILLISECONDS.sleep(pauseDuration);
                    }
                    int finalCurrentX5 = currentX;
                    int finalCurrentY5 = currentY;
                    Platform.runLater(() -> MainApplication.simulationMap.removeVehicle(finalCurrentX5, finalCurrentY5, vehicle));

                    System.out.printf("Vehicle %s has stopped due to a malfunction.%n", vehicle.getVehicleID());
                    MainApplication.userRentals.put(userName, MainApplication.userRentals.getOrDefault(userName, 0) + 1);
                    updateDiscount(userName);
                    generateAndStoreReceipt();
                    return;
                }

                int finalCurrentX2 = currentX;
                int finalCurrentY2 = currentY;
                Platform.runLater(() -> MainApplication.simulationMap.removeVehicle(finalCurrentX2, finalCurrentY2, vehicle));

                int prevX = currentX;
                int prevY = currentY;

                if (currentY != endY) {
                    if (endY > startY) currentY++;
                    else currentY--;
                } else if (currentX != endX) {
                    if (endX > startX) currentX++;
                    else currentX--;
                }

                int finalNewX = currentX;
                int finalNewY = currentY;
                Platform.runLater(() -> MainApplication.simulationMap.addVehicle(finalNewX, finalNewY, vehicle));
                System.out.printf("Vehicle %s moved from (%d, %d) to (%d, %d)%n", vehicle.getVehicleID(), prevX, prevY, currentX, currentY);
                if (!reduceBatteryAndCheck(vehicle, currentX, currentY)) {
                    System.out.printf("Vehicle %s battery depleted, recharging...%n", vehicle.getVehicleID());
                    vehicle.batteryCharging();
                    System.out.printf("Vehicle %s fully charged, reappearing on the map.%n", vehicle.getVehicleID());
                    int finalCurrentX3 = currentX;
                    int finalCurrentY3 = currentY;
                    Platform.runLater(() -> MainApplication.simulationMap.addVehicle(finalCurrentX3, finalCurrentY3, vehicle));
                }
                TimeUnit.MILLISECONDS.sleep(pauseDuration);
            }

            int finalCurrentX4 = currentX;
            int finalCurrentY4 = currentY;
            Platform.runLater(() -> {
                MainApplication.simulationMap.removeVehicle(finalCurrentX4, finalCurrentY4, vehicle);
                MainApplication.simulationMap.addVehicle(endX, endY, vehicle);
                MainApplication.simulationMap.removeVehicle(endX,endY,vehicle);
            });
            System.out.printf("Vehicle %s reached destination (%d, %d)%n", vehicle.getVehicleID(), endX, endY);

            MainApplication.userRentals.put(userName, MainApplication.userRentals.getOrDefault(userName, 0) + 1);
            updateDiscount(userName);
            generateAndStoreReceipt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reduces the vehicle battery and checks if the battery level is above the minimum.
     *
     * @param vehicle The vehicle being checked
     * @param currentX The current X-coordinate of the vehicle
     * @param currentY The current Y-coordinate of the vehicle
     * @return True if the battery is still charged, false otherwise.
     */
    private boolean reduceBatteryAndCheck(Vehicle vehicle,int currentX, int currentY)
    {
        vehicle.reduceBattery();
        if(vehicle.getCurrentBatteryLevel() <= Vehicle.MIN_BATTERY_LEVEL){
            MainApplication.simulationMap.removeVehicle(currentX,currentY,vehicle);
            return false;
        }
        return true;
    }

    /**
     * Generates and stores a receipt for the rental transaction.
     */
    private void generateAndStoreReceipt()
    {
        Receipt receipt = new Receipt(this);
        receipt.generateReceipt();
        synchronized (MainApplication.receipts)
        {
            MainApplication.receipts.add(receipt);
        }
    }

    /**
     * Updates the discount eligibility for the user based on their rental count.
     *
     * @param userName Name of the user to check discount eligibility
     */
    public void updateDiscount(String userName) {
        int rentals = MainApplication.userRentals.getOrDefault(userName, 0);
        if (rentals % 10 == 0) {
            this.hasDiscount = true;
        }
    }

    /**
     * Checks if this `Rental` is equal to another object.
     * Two rentals are considered equal if they have the same rental date and time and vehicle ID.
     *
     * @param o The object to compare with this rental.
     * @return `true` if the rentals are equal, `false` otherwise.
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
        Rental rental = (Rental) o;
        return rentalDateTime.equals(rental.rentalDateTime) && rentalVehicleId.equals(rental.rentalVehicleId);
    }

    /**
     * Returns a hash code value for this `Rental`.
     * The hash code is computed based on the rental date and time and vehicle ID.
     *
     * @return The hash code for this rental.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(rentalDateTime, rentalVehicleId);
    }

    /**
     * Returns a string representation of this `Rental` object,
     * including rental date, username, vehicle ID, start/end locations,
     * duration, identification document, and driving license number.
     *
     * @return A string representation of the rental.
     */
    @Override
    public String toString()
    {
        return "Rental:\n" +
                "date and time: " + rentalDateTime + "\n" +
                "user name: " + userName  + "\n" +
                "vehicle id: " + rentalVehicleId + "\n" +
                "start location: " + startLocation + "\n" +
                "end location: " + endLocation + "\n" +
                "duration: " + rentalDuration + "\n" +
                "identification document: " + identificationDocument + "\n" +
                "driving license number: " + drivingLicenseNumber;
    }
}