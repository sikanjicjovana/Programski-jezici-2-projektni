package net.etf.project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;
import net.etf.project.model.vehicles.Vehicle;

import static net.etf.project.gui.MainApplication.vehicles;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This controller class handles the display of vehicles (cars, bicycles, scooters) in separate tables within the GUI.
 * It categorizes vehicles by type and displays relevant attributes like ID, manufacturer, model, purchase price, and
 * specific attributes for each vehicle type (e.g., range for bicycles, max speed for scooters).
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class VehicleController implements Initializable {

    /**
     * TableColumn for displaying the ID of a bicycle.
     */
    @FXML
    private TableColumn<Bicycle, String> BicycleID;

    /**
     * TableColumn for displaying the manufacturer of a bicycle.
     */
    @FXML
    private TableColumn<Bicycle, String> BicycleManufacturer;

    /**
     * TableColumn for displaying the model of a bicycle.
     */
    @FXML
    private TableColumn<Bicycle, String> BicycleModel;

    /**
     * TableColumn for displaying the purchase price of a bicycle.
     */
    @FXML
    private TableColumn<Bicycle, Double> BicyclePurchasePrice;

    /**
     * TableColumn for displaying the range (in km) of a bicycle.
     */
    @FXML
    private TableColumn<Bicycle, Integer> BicycleRange;

    /**
     * TableColumn for displaying the description of a car.
     */
    @FXML
    private TableColumn<Car, String> CarDescription;

    /**
     * TableColumn for displaying the ID of a car.
     */
    @FXML
    private TableColumn<Car, String> CarID;

    /**
     * TableColumn for displaying the manufacturer of a car.
     */
    @FXML
    private TableColumn<Car, String> CarManufacturer;

    /**
     * TableColumn for displaying the model of a car.
     */
    @FXML
    private TableColumn<Car, String> CarModel;

    /**
     * TableColumn for displaying the purchase date of a car.
     */
    @FXML
    private TableColumn<Car, Date> CarPurchaseDate;

    /**
     * TableColumn for displaying the purchase price of a car.
     */
    @FXML
    private TableColumn<Car, Double> CarPurchasePrice;

    /**
     * TableColumn for displaying the ID of a scooter.
     */
    @FXML
    private TableColumn<Scooter, String> ScooterID;

    /**
     * TableColumn for displaying the manufacturer of a scooter.
     */
    @FXML
    private TableColumn<Scooter, String> ScooterManufacturer;

    /**
     * TableColumn for displaying the maximum speed of a scooter.
     */
    @FXML
    private TableColumn<Scooter, Integer> ScooterMaxSpeed;

    /**
     * TableColumn for displaying the model of a scooter.
     */
    @FXML
    private TableColumn<Scooter, String> ScooterModel;

    /**
     * TableColumn for displaying the purchase price of a scooter.
     */
    @FXML
    private TableColumn<Scooter, Double> ScooterPurchasePrice;

    /**
     * TableView for displaying bicycles.
     */
    @FXML
    private TableView<Vehicle> bicycleTable;

    /**
     * TableView for displaying cars.
     */
    @FXML
    private TableView<Vehicle> carTable;

    /**
     * TableView for displaying scooters.
     */
    @FXML
    private TableView<Vehicle> scooterTable;

    /**
     * ObservableList for storing cars that will be displayed in the car table.
     */
    private ObservableList<Vehicle> carsList = FXCollections.observableArrayList();

    /**
     * ObservableList for storing bicycles that will be displayed in the bicycle table.
     */
    private ObservableList<Vehicle> bicyclesList = FXCollections.observableArrayList();

    /**
     * ObservableList for storing scooters that will be displayed in the scooter table.
     */
    private ObservableList<Vehicle> scootersList = FXCollections.observableArrayList();

    /**
     * Initializes the tables by categorizing vehicles and populating the appropriate tables with the vehicle data.
     * This method is automatically called when the view is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Vehicle vehicle : vehicles)
        {
            if (vehicle instanceof Car) {
                carsList.add(vehicle);
            } else if (vehicle instanceof Bicycle) {
                bicyclesList.add(vehicle);
            } else if (vehicle instanceof Scooter) {
                scootersList.add(vehicle);
            }
        }

        CarID.setCellValueFactory(new PropertyValueFactory<Car,String>("vehicleID"));
        CarPurchasePrice.setCellValueFactory(new PropertyValueFactory<Car,Double>("purchasePrice"));
        CarManufacturer.setCellValueFactory(new PropertyValueFactory<Car,String>("manufacturer"));
        CarModel.setCellValueFactory(new PropertyValueFactory<Car,String>("model"));
        CarPurchaseDate.setCellValueFactory(new PropertyValueFactory<Car,Date>("purchaseDate"));
        CarDescription.setCellValueFactory(new PropertyValueFactory<Car,String>("description"));

        BicycleID.setCellValueFactory(new PropertyValueFactory<Bicycle,String>("vehicleID"));
        BicyclePurchasePrice.setCellValueFactory(new PropertyValueFactory<Bicycle,Double>("purchasePrice"));
        BicycleManufacturer.setCellValueFactory(new PropertyValueFactory<Bicycle,String>("manufacturer"));
        BicycleModel.setCellValueFactory(new PropertyValueFactory<Bicycle,String>("model"));
        BicycleRange.setCellValueFactory(new PropertyValueFactory<Bicycle,Integer>("range"));

        ScooterID.setCellValueFactory(new PropertyValueFactory<Scooter,String>("vehicleID"));
        ScooterPurchasePrice.setCellValueFactory(new PropertyValueFactory<Scooter,Double>("purchasePrice"));
        ScooterManufacturer.setCellValueFactory(new PropertyValueFactory<Scooter,String>("manufacturer"));
        ScooterModel.setCellValueFactory(new PropertyValueFactory<Scooter,String>("model"));
        ScooterMaxSpeed.setCellValueFactory(new PropertyValueFactory<Scooter,Integer>("maxSpeed"));

        carTable.setItems(carsList);
        bicycleTable.setItems(bicyclesList);
        scooterTable.setItems(scootersList);
    }

    /**
     * Switches the scene to the map view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void showMapScene() throws IOException {
        switchScene(MainApplication.HELLO_VIEW_FXML);
    }

    /**
     * Switches the scene to the vehicles table view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void showVehiclesScene() throws IOException {
        switchScene(MainApplication.VEHICLES_TABLE_FXML);
    }

    /**
     * Switches the scene to the malfunctions table view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void showMalfunctionsScene() throws IOException {
        switchScene(MainApplication.MALFUNCTIONS_TABLE_FXML);
    }

    /**
     * Switches the scene to the reports table view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void showReportsScene() throws IOException {
        switchScene(MainApplication.REPORTS_TABLE_FXML);
    }

    /**
     * Switches the scene to the deserialized vehicles table view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void showDeserializedVehiclesScene() throws IOException {
        switchScene(MainApplication.DESERIALIZATION_TABLE_FXML);
    }

    /**
     * Helper method to switch between different scenes.
     *
     * @param fxmlPath The path to the FXML file for the new scene.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void switchScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) MainApplication.getPrimaryStage().getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}