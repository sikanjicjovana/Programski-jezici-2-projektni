package net.etf.project.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.etf.project.model.vehicles.Bicycle;
import net.etf.project.model.vehicles.Car;
import net.etf.project.model.vehicles.Scooter;


import java.io.File;
import java.io.IOException;

/**
 * The MapController class is responsible for managing the graphical representation
 * of a map in the application using a GridPane. It handles the addition and removal
 * of vehicles on the map and manages switching between different scenes.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class MapController {

    /**
     * GridPane representing the grid structure used for vehicle visualization in the simulation.
     * Each cell in the grid represents a part of the city map where vehicles can be displayed.
     */
    @FXML
    private GridPane gridPane;

    /**
     * Menu item for accessing the deserialization view.
     * This option allows the user to view deserialized vehicles from binary files.
     */
    @FXML
    private MenuItem deserializationOption;

    /**
     * Menu item for accessing the malfunctions view.
     * This option allows the user to view a table of vehicles with reported malfunctions.
     */
    @FXML
    private MenuItem malfunctionsOption;

    /**
     * Menu item for accessing the map view.
     * This option allows the user to view the simulation map where vehicles are moving.
     */
    @FXML
    private MenuItem mapOption;

    /**
     * Menu item for accessing the reports view.
     * This option allows the user to view financial reports, including daily and summary reports.
     */
    @FXML
    private MenuItem reportsOption;

    /**
     * Menu item for accessing the vehicles view.
     * This option allows the user to view a table of all vehicles in the system.
     */
    @FXML
    private MenuItem vehiclesOption;

    /**
     * Disables user from selecting some of the controls while the simulation is still running
     * as their outcome is generated at the end.
     */
    public void disableMenuItems() {
        deserializationOption.setDisable(true);
        malfunctionsOption.setDisable(true);
        mapOption.setDisable(true);
        reportsOption.setDisable(true);
        vehiclesOption.setDisable(true);
    }

    /**
     * Enables selecting all possible options when the simulation is complete.
     */
    public void enableMenuItems() {
        deserializationOption.setDisable(false);
        malfunctionsOption.setDisable(false);
        mapOption.setDisable(false);
        reportsOption.setDisable(false);
        vehiclesOption.setDisable(false);
    }

    /**
     * Initializes the map by creating a grid of rectangles with a white(wide part of the city) or light gray(narrow part of the city) background and black border.
     * Each grid cell is wrapped in a StackPane containing a rectangle and a label.
     */
    @FXML
    public void initialize() {

        for (int i = 0; i < MainApplication.MAP_WIDTH; i++) {
            for (int j = 0; j < MainApplication.MAP_HEIGHT; j++) {
                Rectangle rect = new Rectangle(46,34 );
                if ((i >= 0 && i <= 4 || i >= 15 && i <= 19) || (j >= 0 && j <= 4 || j >= 15 && j <= 19)) {
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(Color.LIGHTGRAY);
                }
                rect.setStroke(Color.BLACK);

                Label label = new Label("");
                label.setStyle("-fx-font-size: 10px;");

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, label);

                gridPane.add(stackPane, j, i);
            }
        }
    }

    /**
     * Adds a vehicle to the specified map cell. The vehicle's ID and battery percentage
     * are displayed, and the cell is colored blue for cors, green for bicycles or coral for scooters to indicate the vehicle's presence.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @param vehicleId the ID of the vehicle to be added
     * @param batteryPercentage the battery percentage of the vehicle
     */
    public void addVehicleToField(int x, int y, String vehicleId, double batteryPercentage) {
        StackPane stackPane = getStackPaneAt(x, y);
        if (stackPane != null) {
            Rectangle rect = (Rectangle) stackPane.getChildren().get(0);
            Label label = (Label) stackPane.getChildren().get(1);

            Object vehicle = MainApplication.vehicles.stream()
                    .filter(v -> v.getVehicleID().equals(vehicleId))
                    .findFirst()
                    .orElse(null);

            if (vehicle instanceof Car) {
                rect.setFill(Color.LIGHTSKYBLUE);
            } else if (vehicle instanceof Bicycle) {
                rect.setFill(Color.LIGHTGREEN);
            } else if (vehicle instanceof Scooter) {
                rect.setFill(Color.LIGHTCORAL);
            } else {
                rect.setFill(Color.GRAY);
            }
            String formattedBattery = String.format("%.1f", batteryPercentage);
            label.setText(vehicleId + " (" + formattedBattery + "%)");
        }
    }

    /**
     * Removes a vehicle from the specified map cell. The cell is reset to a white (for wide part of the city) or light grey (for narrow part of the city) background
     * and the label is cleared.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     */
    public void removeVehicleFromField(int x, int y) {
        StackPane stackPane = getStackPaneAt(x, y);
        if (stackPane != null) {
            Rectangle rect = (Rectangle) stackPane.getChildren().get(0);
            Label label = (Label) stackPane.getChildren().get(1);
            if ((x >= 0 && x <= 4 || x >= 15 && x <= 19) || (y >= 0 && y <= 4 || y >= 15 && y <= 19)) {
                rect.setFill(Color.WHITE);
            } else {
                rect.setFill(Color.LIGHTGRAY);
            }
            label.setText("");
        }
    }

    /**
     * Returns the StackPane at the specified grid coordinates.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return the StackPane at the specified coordinates, or null if no such cell exists
     */
    private StackPane getStackPaneAt(int x, int y)
    {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return (StackPane) node;
            }
        }
        return null;
    }

    /**
     * Switches the current scene to the map view scene.
     *
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    public void showMapScene() throws IOException {
        switchScene(MainApplication.HELLO_VIEW_FXML);
    }

    /**
     * Switches the current scene to the vehicles view scene.
     *
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    public void showVehiclesScene() throws IOException {
        switchScene(MainApplication.VEHICLES_TABLE_FXML);
    }

    /**
     * Switches the current scene to the malfunctions view scene.
     *
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    public void showMalfunctionsScene() throws IOException {
        switchScene(MainApplication.MALFUNCTIONS_TABLE_FXML);
    }

    /**
     * Switches the current scene to the reports view scene.
     *
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    public void showReportsScene() throws IOException {
        switchScene(MainApplication.REPORTS_TABLE_FXML);
    }

    /**
     * Switches the current scene to the deserialized vehicles view scene.
     *
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    public void showDeserializedVehiclesScene() throws IOException {
        switchScene(MainApplication.DESERIALIZATION_TABLE_FXML);
    }

    /**
     * Helper method to switch scenes in the application.
     *
     * @param fxmlPath the path to the FXML file of the new scene
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    private void switchScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) MainApplication.getPrimaryStage().getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
