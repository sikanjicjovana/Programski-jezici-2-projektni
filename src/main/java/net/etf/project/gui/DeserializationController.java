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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.etf.project.model.vehicles.Vehicle;
import static net.etf.project.gui.MainApplication.deserializedVehicles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller class handles the deserialized vehicles table view in the GUI.
 * It displays a list of vehicles that were previously deserialized, showing details
 * like vehicle ID, manufacturer, model, purchase price, and repair costs.
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class DeserializationController implements Initializable
{
    /**
     * TableView that displays the deserialized vehicles.
     */
    @FXML
    private TableView<Vehicle> deserializationTable;

    /**
     * TableColumn that displays the vehicle ID.
     */
    @FXML
    private TableColumn<Vehicle, String> id;

    /**
     * TableColumn that displays the manufacturer of the vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> manufacturer;

    /**
     * TableColumn that displays the model of the vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> model;

    /**
     * TableColumn that displays the purchase price of the vehicle.
     */
    @FXML
    private TableColumn<Vehicle, Double> purchasePrice;

    /**
     * TableColumn that displays the repair costs of the vehicle.
     */
    @FXML
    private TableColumn<Vehicle, Double> repairCosts;

    /**
     * ObservableList that holds the deserialized vehicles to be displayed in the table.
     */
    private ObservableList<Vehicle> list = FXCollections.observableArrayList();

    /**
     * Initializes the deserialization table by populating it with deserialized vehicle data.
     * This method is called automatically when the view is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        list.addAll(deserializedVehicles);

        id.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("manufacturer"));
        model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        purchasePrice.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("purchasePrice"));
        repairCosts.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("repairCosts"));

        deserializationTable.setItems(list);
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
     * Helper method for switching scenes.
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
