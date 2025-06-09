package net.etf.project.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.etf.project.financial.Receipt;
import net.etf.project.model.rental.Rental;
import net.etf.project.model.vehicles.Vehicle;
import net.etf.project.serialization.VehicleSerializer;
import net.etf.project.simulation.CityMap;
import net.etf.project.statistics.DailyReport;
import net.etf.project.statistics.DailyReportsGenerator;
import net.etf.project.statistics.SummaryReport;
import net.etf.project.util.RentalLoader;
import net.etf.project.util.VehicleLoader;
import net.etf.project.util.VehicleUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * The {@code MainApplication} class is the main entry point of the application.
 * It initializes the app by loading configuration properties, loading vehicles, rentals,
 * and running the rental simulation, as well as generating summary and daily reports.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class MainApplication extends Application {

    // Application data
    /**
     * The primary stage of the JavaFX application, used to display scenes.
     */
    private static Stage primaryStage;
    /**
     * A list of all vehicles available in the simulation.
     */
    public static List<Vehicle> vehicles = new ArrayList<Vehicle>();
    /**
     * A list of vehicles that have recorded malfunctions.
     */
    public static List<Vehicle> vehiclesWithMalfunctions = new ArrayList<>();
    /**
     * A list of vehicles deserialized from stored data files.
     */
    public static List<Vehicle> deserializedVehicles = new ArrayList<>();
    /**
     * A list of all rentals, tracking the rental history in the system.
     */
    public static List<Rental> rentals = new ArrayList<>();
    /**
     * A map tracking the number of rentals per user, where the key is the username.
     */
    public static Map<String, Integer> userRentals = new HashMap<>();
    /**
     * A list of all generated receipts in the system.
     */
    public static List<Receipt> receipts = new ArrayList<>();
    /**
     * A list of daily reports summarizing daily financial activities.
     */
    public static List<DailyReport> dailyReports = new ArrayList<>();
    /**
     * The map used for vehicle movement simulation, representing the city grid.
     */
    public static CityMap simulationMap;
    /**
     * The summary report containing financial statistics and performance metrics.
     */
    public static SummaryReport summaryReport;

    // Configuration parameters
    /**
     * The file path to the data file containing vehicle information.
     */
    public static String VEHICLE_DATA_PATH;
    /**
     * The file path to the data file containing rental information.
     */
    public static  String RENTAL_DATA_PATH;
    /**
     * The folder path where receipts are saved.
     */
    public static String RECEIPT_FOLDER_PATH;
    /**
     * The width of the city map grid used in the simulation.
     */
    public static int MAP_WIDTH;
    /**
     * The height of the city map grid used in the simulation.
     */
    public static int MAP_HEIGHT;
    /**
     * Coefficient specified for the narrow area in the city.
     */
    public static int DISTANCE_NARROW;
    /**
     * Coefficient specified for the wide area in the city.
     */
    public static int DISTANCE_WIDE;
    /**
     * The discount percentage applied to users with a certain number of rentals.
     */
    public static double DISCOUNT;
    /**
     * The promotional discount percentage applied when specified.
     */
    public static double DISCOUNT_PROM;
    /**
     * The unit price for renting a car.
     */
    public static int CAR_UNIT_PRICE;
    /**
     * The unit price for renting a bicycle.
     */
    public static int BIKE_UNIT_PRICE;
    /**
     * The unit price for renting a scooter.
     */
    public static int SCOOTER_UNIT_PRICE;
    /**
     * The folder path where serialized vehicle files are stored.
     */
    public static String SERIALIZATION_FOLDER_PATH;
    /**
     * The coefficient used to calculate maintenance costs.
     */
    public static double MAINTENANCE_COEFFICIENT;
    /**
     * The coefficient used for calculating the repair costs of cars.
     */
    public static double CAR_COEFFICIENT;
    /**
     * The coefficient used for calculating the repair costs of bicycles.
     */
    public static double BICYCLE_COEFFICIENT;
    /**
     * The coefficient used for calculating the repair costs of cars.
     */
    public static double SCOOTER_COEFFICIENT;
    /**
     * The coefficient used to calculate the company’s costs.
     */
    public static double COMPANY_COSTS_COEFFICIENT;
    /**
     * The coefficient used to calculate the tax applied.
     */
    public static double TAX_COEFFICIENT;
    /**
     * The path to the FXML file for the initial "Hello" view.
     */
    public static String HELLO_VIEW_FXML;
    /**
     * The path to the FXML file for the table displaying vehicles.
     */
    public static String VEHICLES_TABLE_FXML;
    /**
     * The path to the FXML file for the table displaying malfunctions.
     */
    public static String MALFUNCTIONS_TABLE_FXML;
    /**
     * The path to the FXML file for the reports table.
     */
    public static String REPORTS_TABLE_FXML;
    /**
     * The path to the FXML file for the table displaying deserialized vehicles.
     */
    public static String DESERIALIZATION_TABLE_FXML;

    /**
     * Starts the JavaFX application, loads the configuration properties, initializes the
     * simulation, and sets up the GUI.
     *
     * @param stage the primary stage for the application
     * @throws IOException if there is an error loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {

        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));

            VEHICLE_DATA_PATH = properties.getProperty("vehicleDataPath");
            RENTAL_DATA_PATH = properties.getProperty("rentalDataPath");
            RECEIPT_FOLDER_PATH = properties.getProperty("receiptFolderPath");
            MAP_WIDTH = Integer.parseInt(properties.getProperty("mapWidth"));
            MAP_HEIGHT = Integer.parseInt(properties.getProperty("mapHeight"));
            DISTANCE_NARROW = Integer.parseInt(properties.getProperty("DISTANCE_NARROW"));
            DISTANCE_WIDE = Integer.parseInt(properties.getProperty("DISTANCE_WIDE"));
            DISCOUNT = Double.parseDouble(properties.getProperty("DISCOUNT"));
            DISCOUNT_PROM = Double.parseDouble(properties.getProperty("DISCOUNT_PROM"));
            CAR_UNIT_PRICE = Integer.parseInt(properties.getProperty("CAR_UNIT_PRICE"));
            BIKE_UNIT_PRICE = Integer.parseInt(properties.getProperty("BIKE_UNIT_PRICE"));
            SCOOTER_UNIT_PRICE = Integer.parseInt(properties.getProperty("SCOOTER_UNIT_PRICE"));
            SERIALIZATION_FOLDER_PATH = properties.getProperty("serializationFolderPath");
            MAINTENANCE_COEFFICIENT = Double.parseDouble(properties.getProperty("MAINTENANCE_COEFFICIENT"));
            CAR_COEFFICIENT = Double.parseDouble(properties.getProperty("CAR_COEFFICIENT"));
            BICYCLE_COEFFICIENT = Double.parseDouble(properties.getProperty("BICYCLE_COEFFICIENT"));
            SCOOTER_COEFFICIENT = Double.parseDouble(properties.getProperty("SCOOTER_COEFFICIENT"));
            COMPANY_COSTS_COEFFICIENT = Double.parseDouble(properties.getProperty("COMPANY_COSTS_COEFFICIENT"));
            TAX_COEFFICIENT = Double.parseDouble(properties.getProperty("TAX_COEFFICIENT"));
            HELLO_VIEW_FXML = properties.getProperty("HELLO_VIEW_FXML");
            VEHICLES_TABLE_FXML = properties.getProperty("VEHICLES_TABLE_FXML");
            MALFUNCTIONS_TABLE_FXML = properties.getProperty("MALFUNCTIONS_TABLE_FXML");
            REPORTS_TABLE_FXML = properties.getProperty("REPORTS_TABLE_FXML");
            DESERIALIZATION_TABLE_FXML = properties.getProperty("DESERIALIZATION_TABLE_FXML");

            System.out.println();
            System.out.println("INFORMATION FROM PROPERTIES FILE");
            System.out.println("--------------------------------");

            System.out.println("Vehicle data path: " + VEHICLE_DATA_PATH);
            System.out.println("Rental data path: " + RENTAL_DATA_PATH);
            System.out.println("Receipt folder path: " + RECEIPT_FOLDER_PATH);
            System.out.println("Map width: " + MAP_WIDTH);
            System.out.println("Map height: " + MAP_HEIGHT);
            System.out.println("Distance narrow: " + DISTANCE_NARROW);
            System.out.println("Distance wide: " + DISTANCE_WIDE);
            System.out.println("Discount: " + DISCOUNT);
            System.out.println("Discount prom: " + DISCOUNT_PROM);
            System.out.println("Car unit price: " + CAR_UNIT_PRICE);
            System.out.println("Bike unit price: " + BIKE_UNIT_PRICE);
            System.out.println("Scooter unit price: " + SCOOTER_UNIT_PRICE);
            System.out.println("Serialization folder path: " + SERIALIZATION_FOLDER_PATH);
            System.out.println("Maintenance coefficient: " + MAINTENANCE_COEFFICIENT);
            System.out.println("Car coefficient: " + CAR_COEFFICIENT);
            System.out.println("Bicycle coefficient: " + BICYCLE_COEFFICIENT);
            System.out.println("Scooter coefficient: " + SCOOTER_COEFFICIENT);
            System.out.println("Company costs coefficient: " + COMPANY_COSTS_COEFFICIENT);
            System.out.println("Tax coefficient: " + TAX_COEFFICIENT);
            System.out.println("Hello view fxml: " + HELLO_VIEW_FXML);
            System.out.println("Vehicles table fxml: " + VEHICLES_TABLE_FXML);
            System.out.println("Malfunctions table fxml: " + MALFUNCTIONS_TABLE_FXML);
            System.out.println("Reports table fxml: " + REPORTS_TABLE_FXML);
            System.out.println("Deserialization table fxml: " + DESERIALIZATION_TABLE_FXML);

            System.out.println();
            System.out.println("PREPARING FOLDERS FOR NEW SIMULATION");
            System.out.println("------------------------------------");
            clearFolder(RECEIPT_FOLDER_PATH);
            clearFolder(SERIALIZATION_FOLDER_PATH);

            System.out.println();
            System.out.println("PARSING INFORMATION");
            System.out.println("--------------------------------------------------------------------------------------------------------");

            VehicleLoader.loadVehicles();
            RentalLoader.loadRentals();

            System.out.println("--------------------------------------------------------------------------------------------------------");

        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }

        System.out.println();
        System.out.println("VEHICLES");
        System.out.println("--------");

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }

        System.out.println();
        System.out.println("RENTALS");
        System.out.println("--------");

        for (Rental rental : rentals) {
            System.out.println(rental.toString());
            System.out.println("************************************************");
        }

        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------");

        vehiclesWithMalfunctions = VehicleUtils.findVehiclesWithMalfunctions();
        System.out.println("Vehicles with malfunctions: ");
        for(Vehicle v : vehiclesWithMalfunctions)
            System.out.println(v);

        VehicleSerializer.serializeVehiclesWithMalfunctions(vehiclesWithMalfunctions,SERIALIZATION_FOLDER_PATH);

        deserializedVehicles = VehicleSerializer.deserializeVehicles(SERIALIZATION_FOLDER_PATH);
        for(Vehicle vehicle : deserializedVehicles)
            System.out.println("Deserialized vehicle: " + vehicle);

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(HELLO_VIEW_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ePJ2 company app");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.jpg")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        // Initialize map controller
        MapController mapController = fxmlLoader.getController();
        simulationMap = new CityMap(mapController);

        // Start the simulation on a new thread
        new Thread(() -> {
            simulateRentals(mapController);
            summaryReport = new SummaryReport();
            summaryReport.generateSummaryReport();
            summaryReport.printSummaryReport();

            dailyReports = DailyReportsGenerator.generateAllDailyReports();
            for(DailyReport dailyReport : dailyReports)
            {
                dailyReport.printDailyReport();
            }
        }).start();
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary {@code Stage} instance
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The main entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Simulates vehicle rentals by running each rental in its own thread.
     * Rentals are simulated in chronological order, with a 5-second pause between
     * different rental periods.
     */
    public static void simulateRentals(MapController mapController) {
        mapController.disableMenuItems();
        Date currentDateTime = null;
        List<Thread> activeThreads = new ArrayList<>();

        for (Rental rental : rentals) {
            // If the rental time has changed, pause the simulation for 5 seconds
            if (currentDateTime == null || !rental.getRentalDateTime().equals(currentDateTime)) {
                if (currentDateTime != null) {
                    // Wait for active threads to complete before continuing
                    for(Thread thread : activeThreads)
                    {
                        try {
                            thread.join();
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    activeThreads.clear();

                    System.out.println("Pausing for 5 seconds...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentDateTime = rental.getRentalDateTime();
            }
            // Start a new thread for each rental
            Thread rentalThread = new Thread(rental);
            activeThreads.add(rentalThread);
            rentalThread.start();
        }
        // Wait for all active threads to complete
        for(Thread thread : activeThreads)
        {
            try{
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        mapController.enableMenuItems();
        System.out.println("Simulation complete.");
    }

    /**
     * Deletes all files in the specified folder.
     *
     * @param folderPath The absolute path to the folder whose contents will be deleted.
     */
    public static void clearFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (file.delete()) {
                            System.out.println("Deleted file: " + file.getName());
                        } else {
                            System.err.println("Failed to delete file: " + file.getName());
                        }
                    }
                }
            } else {
                System.out.println("The folder is already empty: " + folderPath);
            }
        } else {
            System.err.println("The specified path is not a directory.");
        }
    }
}