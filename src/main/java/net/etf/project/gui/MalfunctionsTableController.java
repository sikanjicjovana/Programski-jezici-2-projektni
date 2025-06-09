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
import net.etf.project.model.vehicles.Malfunction;
import net.etf.project.model.vehicles.MalfunctionWrapper;
import net.etf.project.model.vehicles.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static net.etf.project.gui.MainApplication.vehiclesWithMalfunctions;

/**
 * This controller class handles the malfunctions table view in the GUI.
 * It displays a list of all malfunctions from the vehicles with malfunctions.
 * Each malfunction includes the vehicle type, vehicle ID, description, and date/time.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class MalfunctionsTableController implements Initializable
{
    /**
     * TableView that displays the malfunctions.
     */
    @FXML
    private TableView<MalfunctionWrapper> malfunctionsTable;

    /**
     * TableColumn that displays the date and time of the malfunction.
     */
    @FXML
    private TableColumn<MalfunctionWrapper, Date> dateAndTime;

    /**
     * TableColumn that displays the description of the malfunction.
     */
    @FXML
    private TableColumn<MalfunctionWrapper, String> description;

    /**
     * TableColumn that displays the type of vehicle (Car, Bicycle, Scooter, etc.).
     */
    @FXML
    private TableColumn<MalfunctionWrapper, String> type;

    /**
     * TableColumn that displays the vehicle's ID.
     */
    @FXML
    private TableColumn<MalfunctionWrapper, String> vehicleID;

    /**
     * ObservableList that holds the malfunctions to be displayed in the table.
     */
    private ObservableList<MalfunctionWrapper> list = FXCollections.observableArrayList();

    /**
     * Initializes the table by populating it with malfunction data.
     * This method is called automatically when the view is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Vehicle vehicle : vehiclesWithMalfunctions) {
            List<Malfunction> malfunctions = vehicle.getMalfunctions();

            for (Malfunction malfunction : malfunctions) {

                MalfunctionWrapper reaper = new MalfunctionWrapper(
                        vehicle.getClass().getSimpleName(),
                        vehicle.getVehicleID(),
                        malfunction.getDescription() ,
                        malfunction.getMalfunctionDateTime()
                );
                list.add(reaper);
            }
        }

        dateAndTime.setCellValueFactory(new PropertyValueFactory<MalfunctionWrapper,Date>("dateAndTime"));
        description.setCellValueFactory(new PropertyValueFactory<MalfunctionWrapper,String>("description"));
        type.setCellValueFactory(new PropertyValueFactory<MalfunctionWrapper,String>("type"));
        vehicleID.setCellValueFactory(new PropertyValueFactory<MalfunctionWrapper,String>("vehicleID"));

        malfunctionsTable.setItems(list);
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
