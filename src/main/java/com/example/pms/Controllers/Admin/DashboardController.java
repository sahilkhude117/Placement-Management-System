package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.PlacedStudentsCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button reload_btn;
    public Label dashHighP_lbl;
    public Label dashPS_lbl;
    public Label dashCompany_lbl;
    public Label dashUPS_lbl;
    public Label dashPSPercent_lbl;
    public Label dashCompanyPercent_lbl;
    public PieChart dashboard_pie;
    public ListView<PlacedStudents> dashboardListView;


    public static DashboardController instance;
    public ObservableList<PlacedStudents> placedStudentsObservableList;
    public MenuButton sortByDeptDash_mb;

    private FilteredList<PlacedStudents> filteredPSList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // label
        updateLabels();

        //pie

        loadPieChartData();
        populateDepartmentMenuButton();
        reload_btn.setOnAction(event -> {
                    sortByDeptDash_mb.setText("Sort By Department");
                    loadPieChartData();
                    updateLabels();
                });

        /*
        * List view
        * */

        instance = this;
        placedStudentsObservableList = FXCollections.observableArrayList();
        dashboardListView.setItems(placedStudentsObservableList);
        dashboardListView.setCellFactory(placedStudentsListView -> new PlacedStudentsCellFactory());
        refresh();
    }

    private void populateDepartmentMenuButton() {

        sortByDeptDash_mb.getItems().clear();

        // Create MenuItems for each department
        MenuItem it = new MenuItem("IT");
        MenuItem mech = new MenuItem("MECH");
        MenuItem comps = new MenuItem("COMPS");
        MenuItem extc = new MenuItem("EXTC");
        MenuItem def = new MenuItem("Default");

        // Add MenuItems to the MenuButton
        sortByDeptDash_mb.getItems().addAll(it, mech, comps, extc);

        // Optionally: Set an action for when an item is selected
        it.setOnAction(event -> handleDepartmentSelection("IT"));
        mech.setOnAction(event -> handleDepartmentSelection("Mech"));
        comps.setOnAction(event -> handleDepartmentSelection("Comps"));
        extc.setOnAction(event -> handleDepartmentSelection("EXTC"));
        def.setOnAction(event -> handleDepartmentSelection("Default"));
    }

    private void handleDepartmentSelection(String department) {
        sortByDeptDash_mb.setText(department);
        loadPieChartData(department);
        updateLabelsForDepartment(department);

        // list
        if (department.equals("Default")) {
            sortByDeptDash_mb.setText("Sort By Department");
            loadPieChartData();
            updateLabels();
            filteredPSList.setPredicate(placedStudents -> true);
        } else {
            // Filter students by the selected department
            filteredPSList.setPredicate(placedStudents -> placedStudents.placedStudentDeptProperty().get().equals(department));
        }

        // Convert FilteredList to ObservableList
        ObservableList<PlacedStudents> sortedStudents = FXCollections.observableArrayList(filteredPSList);

        // Set the sorted list to the ListView
        dashboardListView.setItems(sortedStudents);

    }

    public void refresh() {
        // Retrieve updated data from the database
        List<PlacedStudents> retrievedStudents = Model.getInstance().getDatabaseDriver().getAllPlacedStudents();

        // Clear the current data in the ObservableList
        placedStudentsObservableList.clear();

        // Add the updated data to the ObservableList
        placedStudentsObservableList.addAll(retrievedStudents);

        filteredPSList = new FilteredList<>(placedStudentsObservableList, student -> true);

        // Refresh the ListView
        dashboardListView.setItems(filteredPSList);
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
        dashboard_pie.setData(pieChartData);
    }

    /*
    * Update Labels
    * */

    private void updateLabels() {
        // Get the current year and last year as Strings
        String currentYear = String.valueOf(java.time.Year.now().getValue());
        String lastYear = String.valueOf(java.time.Year.now().minusYears(1).getValue());

        // Fetch placed count and company count by year (for percentage calculations)
        Map<String, Integer> placedCountByYear = Model.getInstance().getDatabaseDriver().getPlacedCountByYear();
        Map<String, Integer> companyCountByYear = Model.getInstance().getDatabaseDriver().getCompaniesCountByYear();

        // Fetch data for current year and last year, with default values if not found
        int placedCountThisYear = placedCountByYear.getOrDefault(currentYear, 0);
        int placedCountLastYear = placedCountByYear.getOrDefault(lastYear, 0);

        int companyCountThisYear = companyCountByYear.getOrDefault(currentYear, 0);
        int companyCountLastYear = companyCountByYear.getOrDefault(lastYear, 0);

        // Fetch overall data (not by year)
        int totalPlacedCount = Model.getInstance().getDatabaseDriver().getPlacedCount(); // Fetches total placed students
        int totalUnplacedCount = Model.getInstance().getDatabaseDriver().getUnplacedCount(); // Fetches total unplaced students
        int totalCompanyCount = Model.getInstance().getDatabaseDriver().getCompanyCount(); // Fetches total companies
        int highestPackage = Model.getInstance().getDatabaseDriver().getHighestPackage(); // Fetches highest package

        // Update the labels with the overall data
        dashHighP_lbl.setText(String.valueOf(highestPackage) + " Lpa ");
        dashPS_lbl.setText(String.valueOf(totalPlacedCount)); // Total placed students (not just this year)
        dashUPS_lbl.setText(String.valueOf(totalUnplacedCount)); // Total unplaced students
        dashCompany_lbl.setText(String.valueOf(totalCompanyCount)); // Total companies

        // Update percentage growth for placed students and companies based on year comparison
        updatePercentageLabel(dashPSPercent_lbl, placedCountThisYear, placedCountLastYear);
        updatePercentageLabel(dashCompanyPercent_lbl, companyCountThisYear, companyCountLastYear);
    }

    private void updatePercentageLabel(Label label, int currentCount, int lastYearCount) {
        if (lastYearCount == 0) {
            label.setText("N/A"); // Handle case where last year's count is zero
            label.getStyleClass().removeAll("positive-percentage", "negative-percentage"); // Remove any previous classes
            return;
        }

        double percentChange = ((double)(currentCount - lastYearCount) / lastYearCount) * 100;
        String sign = percentChange >= 0 ? "+" : "-";
        label.setText(sign + String.format("%.2f%%", Math.abs(percentChange)));

        // Clear old styles
        label.getStyleClass().removeAll("positive-percentage", "negative-percentage");

        // Apply the correct style class
        if (percentChange >= 0) {
            label.getStyleClass().add("positive-percentage");
        } else {
            label.getStyleClass().add("negative-percentage");
        }
    }

    private void updateLabelsForDepartment(String department) {
        // Get the current year and last year as Strings
        String currentYear = String.valueOf(java.time.Year.now().getValue());
        String lastYear = String.valueOf(java.time.Year.now().minusYears(1).getValue());

        // Fetch placed count and company count by year (for percentage calculations)
        Map<String, Integer> placedCountByYear = Model.getInstance().getDatabaseDriver().getPlacedCountByYearForDept(department);

        // Fetch data for current year and last year, with default values if not found
        int placedCountThisYear = placedCountByYear.getOrDefault(currentYear, 0);
        int placedCountLastYear = placedCountByYear.getOrDefault(lastYear, 0);

        // Fetch department-specific data
        int totalPlacedCount = Model.getInstance().getDatabaseDriver().getPlacedCountByDept(department);
        int totalUnplacedCount = Model.getInstance().getDatabaseDriver().getUnplacedCountByDept(department);
        int highestPackage = Model.getInstance().getDatabaseDriver().getHighestPackageByDept(department);

        // Update the labels with department-specific data
        dashHighP_lbl.setText(String.valueOf(highestPackage) + " Lpa ");
        dashPS_lbl.setText(String.valueOf(totalPlacedCount));  // Total placed students for the department
        dashUPS_lbl.setText(String.valueOf(totalUnplacedCount));  // Total unplaced students for the department

        // Update percentage growth for placed students based on department-specific data
        updatePercentageLabel(dashPSPercent_lbl, placedCountThisYear, placedCountLastYear);
    }



}
