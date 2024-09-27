package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;

import java.io.ObjectStreamClass;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class AnalyticsController implements Initializable {
    public PieChart pie_chart;
    public Button reload_btn;
    public BarChart<String , Number> bar_chart;
    public LineChart<String, Number> line_chart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public CategoryAxis xLineAxis;
    public NumberAxis yLineAxis;
    public MenuButton sortByDeptAnalytics_mb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPieChartData();
        loadBarChartData();
        loadLineChartData();
        populateDepartmentMenuButton();

        createHoverEffect(sortByDeptAnalytics_mb);
        createHoverEffect(reload_btn);

        reload_btn.setOnAction(event -> onReload());
    }

    private void populateDepartmentMenuButton() {

        sortByDeptAnalytics_mb.getItems().clear();

        // Create MenuItems for each department
        MenuItem it = new MenuItem("IT");
        MenuItem mech = new MenuItem("MECH");
        MenuItem comps = new MenuItem("COMPS");
        MenuItem extc = new MenuItem("EXTC");

        // Add MenuItems to the MenuButton
        sortByDeptAnalytics_mb.getItems().addAll(it, mech, comps, extc);

        // Optionally: Set an action for when an item is selected
        it.setOnAction(event -> handleDepartmentSelection("IT"));
        mech.setOnAction(event -> handleDepartmentSelection("MECH"));
        comps.setOnAction(event -> handleDepartmentSelection("COMPS"));
        extc.setOnAction(event -> handleDepartmentSelection("EXTC"));
    }

    private void handleDepartmentSelection(String department) {
        sortByDeptAnalytics_mb.setText(department);
        loadPieChartData(department);
        loadBarChartData(department);
        loadLineChartData(department);
    }

    // Method to load data into the PieChart
    private void loadPieChartData() {
        // Fetch data from DatabaseDriver
        int placedCount = Model.getInstance().getDatabaseDriver().getPlacedCount();
        int unplacedCount = Model.getInstance().getDatabaseDriver().getUnplacedCount();
        int higherStudyCount = Model.getInstance().getDatabaseDriver().getHigherStudyCount();

        // Create data for PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Placed", placedCount),
                new PieChart.Data("Unplaced", unplacedCount),
                new PieChart.Data("Opt to Higher Study", higherStudyCount)
        );

        // Set the data to the pie chart
        pie_chart.setData(pieChartData);
    }

    // Method to load department-specific data into the PieChart
    private void loadPieChartData(String department) {
        // Fetch department-specific data from DatabaseDriver
        int placedCount = Model.getInstance().getDatabaseDriver().getPlacedCountByDept(department);
        int unplacedCount = Model.getInstance().getDatabaseDriver().getUnplacedCountByDept(department);
        int higherStudyCount = Model.getInstance().getDatabaseDriver().getHigherStudyCountByDept(department);

        // Create data for PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Placed", placedCount),
                new PieChart.Data("Unplaced", unplacedCount),
                new PieChart.Data("Opt to Higher Study", higherStudyCount)
        );

        // Set the data to the pie chart
        pie_chart.setData(pieChartData);
    }


    private void loadBarChartData() {

        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Placed Students Per Year");

        // Fetch the data from DatabaseDriver
        // Use a TreeMap to ensure the years are sorted in increasing order
        Map<String, Integer> yearlyData = new TreeMap<>(Model.getInstance().getDatabaseDriver().getPlacedCountByYear());

        // Check if data is available
        if (yearlyData != null && !yearlyData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : yearlyData.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
            }
        } else {
            showAlert("Note","No data available");
        }

        // Add the series to the bar chart data
        barChartData.add(series);

        // Set the bar chart data
        bar_chart.setData(barChartData);
    }

    private void loadBarChartData(String department) {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Placed Students Per Year");

        // Fetch department-specific placed students data from DatabaseDriver
        Map<String, Integer> yearlyData = new TreeMap<>(Model.getInstance().getDatabaseDriver().getPlacedCountByYearForDept(department));

        // Check if data is available
        if (yearlyData != null && !yearlyData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : yearlyData.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
            }
        } else {
            showAlert("Note","No data available for " + department +" departments bar chart");
        }

        // Add the series to the bar chart data
        barChartData.add(series);

        // Set the bar chart data
        bar_chart.setData(barChartData);
    }

    private void loadLineChartData() {
        // Create two series for placed students and number of companies
        XYChart.Series<String, Number> placedStudentsSeries = new XYChart.Series<>();
        placedStudentsSeries.setName("Placed Students Per Year");


        XYChart.Series<String, Number> companiesSeries = new XYChart.Series<>();
        companiesSeries.setName("Companies Per Year");

        // Fetch the placed students data (from PlacedStudents table)
        Map<String, Integer> placedStudentsData = new TreeMap<>(Model.getInstance().getDatabaseDriver().getPlacedCountByYear());

        // Fetch the number of companies data (from Company table)
        Map<String, Integer> companiesData = new TreeMap<>(Model.getInstance().getDatabaseDriver().getCompaniesCountByYear());

        // Check if placed students data is available and populate the series
        if (!placedStudentsData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : placedStudentsData.entrySet()) {
                placedStudentsSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } else {
            showAlert("Note","No data available for the bar chart.");
        }

        // Check if companies data is available and populate the series
        if (!companiesData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : companiesData.entrySet()) {
                companiesSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } else {
            showAlert("Note","No data available for the bar chart.");
        }

        // Add both series to the line chart
        line_chart.getData().clear();  // Clear existing data
        line_chart.getData().addAll(placedStudentsSeries, companiesSeries);
    }

    private void loadLineChartData(String department) {
        // Create two series for placed students and number of companies
        XYChart.Series<String, Number> placedStudentsSeries = new XYChart.Series<>();
        placedStudentsSeries.setName("Placed Students Per Year");


        // Fetch department-specific data
        Map<String, Integer> placedStudentsData = new TreeMap<>(Model.getInstance().getDatabaseDriver().getPlacedCountByYearForDept(department));

        // Check if placed students data is available and populate the series
        if (!placedStudentsData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : placedStudentsData.entrySet()) {
                placedStudentsSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } else {
            showAlert("Note","No data available for " + department + " departments line chart ");
            onReload();
        }


        // Add both series to the line chart
        line_chart.getData().clear();  // Clear existing data
        line_chart.getData().addAll(placedStudentsSeries);
    }

    public void createHoverEffect(Button btn) {
        // Create scale transition for the button
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), btn);
        scaleIn.setToX(1.1);  // Scale to 110% on hover
        scaleIn.setToY(1.1);  // Scale to 110% on hover

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), btn);
        scaleOut.setToX(1);   // Scale back to normal when not hovered
        scaleOut.setToY(1);   // Scale back to normal when not hovered

        // Set hover event listeners
        btn.setOnMouseEntered(e -> scaleIn.playFromStart());
        btn.setOnMouseExited(e -> scaleOut.playFromStart());
    }

    public void createHoverEffect(MenuButton mb) {
        // Create scale transition for the button
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), mb);
        scaleIn.setToX(1.1);  // Scale to 110% on hover
        scaleIn.setToY(1.1);  // Scale to 110% on hover

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), mb);
        scaleOut.setToX(1);   // Scale back to normal when not hovered
        scaleOut.setToY(1);   // Scale back to normal when not hovered

        // Set hover event listeners
        mb.setOnMouseEntered(e -> scaleIn.playFromStart());
        mb.setOnMouseExited(e -> scaleOut.playFromStart());
    }



    private void onReload() {
        sortByDeptAnalytics_mb.setText("Sort By Department");
        loadPieChartData();
        loadBarChartData();
        loadLineChartData();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

