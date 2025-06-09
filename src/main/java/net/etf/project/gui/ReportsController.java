package net.etf.project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.etf.project.model.vehicles.Vehicle;
import net.etf.project.statistics.DailyReport;
import net.etf.project.statistics.SummaryReport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static net.etf.project.gui.MainApplication.*;

/**
 * The ReportsController class is responsible for controlling the GUI of the reports screen.
 * It manages the display of both daily and summary reports in the application.
 * The reports data is fetched and populated in the respective TableView elements.
 * The class also provides methods for switching between different scenes of the application.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class ReportsController implements Initializable
{
    /**
     * Table column for company costs in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> companyCosts;

    /**
     * Table view for daily report.
     */
    @FXML
    private TableView<DailyReport> dailyTable;

    /**
     * Table column for date in the daily report.
     */
    @FXML
    private TableColumn<DailyReport, String> date;

    /**
     * Table column for daily discounts.
     */
    @FXML
    private TableColumn<DailyReport, Double> discountsDaily;

    /**
     * Table column for total discounts in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> discountsSummary;

    /**
     * Table column for daily income.
     */
    @FXML
    private TableColumn<DailyReport, Double> incomeDaily;

    /**
     * Table column for total income in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> incomeSummary;

    /**
     * Table column for daily maintenance costs.
     */
    @FXML
    private TableColumn<DailyReport, Double> maintenanceDaily;

    /**
     * Table column for total maintenance costs in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> maintenanceSummary;

    /**
     * Table column for daily income in the narrow area.
     */
    @FXML
    private TableColumn<DailyReport, Double> narrowAreaDaily;

    /**
     * Table column for total income in the narrow area in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> narrowAreaSummary;

    /**
     * Table column for daily promotions.
     */
    @FXML
    private TableColumn<DailyReport, Double> promotionsDaily;

    /**
     * Table column for total promotions in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> promotionsSummary;

    /**
     * Table column for daily repair costs.
     */
    @FXML
    private TableColumn<DailyReport, Double> repairDaily;

    /**
     * Table column for total repair costs in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> repairSummary;

    /**
     * Table view for summary report.
     */
    @FXML
    private TableView<SummaryReport> summaryTable;

    /**
     * Table column for tax in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> tax;

    /**
     * Table column for daily income in the wide area.
     */
    @FXML
    private TableColumn<DailyReport, Double> wideAreaDaily;

    /**
     * Table column for total income in the wide area in the summary report.
     */
    @FXML
    private TableColumn<SummaryReport, Double> wideAreaSummary;

    /**
     * Observable list holding the summary report data.
     */
    private ObservableList<SummaryReport> listSummary = FXCollections.observableArrayList();

    /**
     * Observable list holding the daily report data.
     */
    private ObservableList<DailyReport> listDaily = FXCollections.observableArrayList();

    /**
     * Initializes the ReportsController. Populates the TableView elements with data from the summary
     * and daily reports.
     *
     * @param url the location of the FXML file
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        listSummary.addAll(summaryReport);
        listDaily.addAll(dailyReports);

        incomeSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalIncome"));
        discountsSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalDiscounts"));
        promotionsSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalPromotions"));
        wideAreaSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalWideAreaIncome"));
        narrowAreaSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalNarrowAreaIncome"));
        maintenanceSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalMaintenanceCost"));
        repairSummary.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalRepairCost"));
        companyCosts.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalCompanyCosts"));
        tax.setCellValueFactory(new PropertyValueFactory<SummaryReport, Double>("totalTax"));

        date.setCellValueFactory(new PropertyValueFactory<DailyReport, String>("date"));
        incomeDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyIncome"));
        discountsDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyDiscounts"));
        promotionsDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyPromotions"));
        wideAreaDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyWideAreaIncome"));
        narrowAreaDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyNarrowAreaIncome"));
        maintenanceDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyMaintenanceCost"));
        repairDaily.setCellValueFactory(new PropertyValueFactory<DailyReport, Double>("dailyRepairCosts"));

        summaryTable.setItems(listSummary);
        dailyTable.setItems(listDaily);

    }

    /**
     * Switches to the map scene when the user requests it.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showMapScene() throws IOException {
        switchScene(MainApplication.HELLO_VIEW_FXML);
    }

    /**
     * Switches to the vehicles scene when the user requests it.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showVehiclesScene() throws IOException {
        switchScene(MainApplication.VEHICLES_TABLE_FXML);
    }

    /**
     * Switches to the malfunctions scene when the user requests it.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showMalfunctionsScene() throws IOException {
        switchScene(MainApplication.MALFUNCTIONS_TABLE_FXML);
    }

    /**
     * Switches to the reports scene when the user requests it.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showReportsScene() throws IOException {
        switchScene(MainApplication.REPORTS_TABLE_FXML);
    }

    /**
     * Switches to the deserialized vehicles scene when the user requests it.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showDeserializedVehiclesScene() throws IOException {
        switchScene(MainApplication.DESERIALIZATION_TABLE_FXML);
    }

    /**
     * Switches to a new scene based on the given FXML path.
     *
     * @param fxmlPath the FXML file path of the desired scene
     * @throws IOException if the FXML file cannot be loaded
     */
    private void switchScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) MainApplication.getPrimaryStage().getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
