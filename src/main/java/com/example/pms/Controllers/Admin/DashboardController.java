package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.PlacedStudentsCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label placed_lbl;
    public Label jobs_lbl;
    public Label unplaced_lbl;
    public Label company_lbl;
    public PieChart dashboard_pie;
    public ListView<PlacedStudents> dashboardListView;


    public static DashboardController instance;
    public ObservableList<PlacedStudents> placedStudentsObservableList;
    public Button reload_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 4 pillers

        //pie
        loadPieChartData();
        reload_btn.setOnAction(event -> loadPieChartData());

        /*
        * List view
        * */

        instance = this;
        placedStudentsObservableList = FXCollections.observableArrayList();
        dashboardListView.setItems(placedStudentsObservableList);
        dashboardListView.setCellFactory(placedStudentsListView -> new PlacedStudentsCellFactory());
        refresh();
    }

    public void refresh() {
        // Retrieve updated data from the database
        List<PlacedStudents> retrievedStudents = Model.getInstance().getDatabaseDriver().getAllPlacedStudents();

        // Clear the current data in the ObservableList
        placedStudentsObservableList.clear();

        // Add the updated data to the ObservableList
        placedStudentsObservableList.addAll(retrievedStudents);

        // Refresh the ListView
        dashboardListView.setItems(placedStudentsObservableList);
    }

    public ObservableList<PlacedStudents> getPlacedStudentsObservableList() {
        return placedStudentsObservableList;
    }

    public ListView<PlacedStudents> getPlacedStudentsListView() {
        return dashboardListView;
    }

    /*
    * Pie
    * */

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
        dashboard_pie.setData(pieChartData);
    }
}
