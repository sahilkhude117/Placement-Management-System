package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPieChartData();
        loadBarChartData();
        loadLineChartData();

        reload_btn.setOnAction(event -> onReload());
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
            System.out.println("No data available for the bar chart.");
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
            System.out.println("No placed students data available for the line chart.");
        }

        // Check if companies data is available and populate the series
        if (!companiesData.isEmpty()) {
            for (Map.Entry<String, Integer> entry : companiesData.entrySet()) {
                companiesSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } else {
            System.out.println("No companies data available for the line chart.");
        }

        // Add both series to the line chart
        line_chart.getData().clear();  // Clear existing data
        line_chart.getData().addAll(placedStudentsSeries, companiesSeries);
    }



    private void onReload() {
        loadPieChartData();
        loadBarChartData();
        loadLineChartData();
    }
}

