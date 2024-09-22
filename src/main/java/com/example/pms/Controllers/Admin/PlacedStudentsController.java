package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.PlacedStudentsCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.stage.FileChooser;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import java.net.URL;
import java.util.ResourceBundle;

public class PlacedStudentsController implements Initializable {
    @FXML
    public Button addPlacedStudents_btn;

    public ListView<PlacedStudents> placedStudentsListView;

    public static PlacedStudentsController instance;

    public ObservableList<PlacedStudents> placedStudentsObservableList;
    public MenuButton sortPS_mb;
    public Button ExportPS_btn;

    private FilteredList<PlacedStudents> filteredPSList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        placedStudentsObservableList = FXCollections.observableArrayList();
        placedStudentsListView.setItems(placedStudentsObservableList);
        placedStudentsListView.setCellFactory(placedStudentsListView -> new PlacedStudentsCellFactory());

        addPlacedStudents_btn.setOnAction(event -> onAddPlacedStudent());

        refresh();
        populateDepartmentMenuButton();

    }

    private void populateDepartmentMenuButton() {

        sortPS_mb.getItems().clear();

        // Create MenuItems for each department
        MenuItem it = new MenuItem("IT");
        MenuItem mech = new MenuItem("MECH");
        MenuItem comps = new MenuItem("COMPS");
        MenuItem extc = new MenuItem("EXTC");


        // Add MenuItems to the MenuButton for departments
        sortPS_mb.getItems().addAll(it, mech, comps, extc);

        // Optionally: Set an action for when an item is selected
        it.setOnAction(event -> handleDepartmentSelection("IT"));
        mech.setOnAction(event -> handleDepartmentSelection("Mech"));
        comps.setOnAction(event -> handleDepartmentSelection("Comps"));
        extc.setOnAction(event -> handleDepartmentSelection("EXTC"));


        // Create MenuItems for sorting options
        MenuItem sortById = new MenuItem("Sort by ID");
        MenuItem sortByName = new MenuItem("Sort by Name");
        MenuItem sortByGpa = new MenuItem("Sort by Package");
        MenuItem sortByStatus = new MenuItem("Sort by Role");
        MenuItem def = new MenuItem("Default");

        // Add sorting options to the MenuButton
        sortPS_mb.getItems().addAll(sortById, sortByName, sortByGpa, sortByStatus,def);

        // Set actions for each sorting option
        sortById.setOnAction(event -> sortStudents("ID"));
        sortByName.setOnAction(event -> sortStudents("Name"));
        sortByGpa.setOnAction(event -> sortStudents("GPA"));
        sortByStatus.setOnAction(event -> sortStudents("Status"));
        def.setOnAction(event -> handleDepartmentSelection("Default"));
    }


    private void handleDepartmentSelection(String department) {
        sortPS_mb.setText(department);

        if (department.equals("Default")) {
            // Show all students if "ALL" is selected
            sortPS_mb.setText("Sort By");
            filteredPSList.setPredicate(placedStudents -> true);
        } else {
            // Filter students by the selected department
            filteredPSList.setPredicate(placedStudents -> placedStudents.placedStudentDeptProperty().get().equals(department));
        }

        // Convert FilteredList to ObservableList
        ObservableList<PlacedStudents> sortedStudents = FXCollections.observableArrayList(filteredPSList);

        // Set the sorted list to the ListView
        placedStudentsListView.setItems(sortedStudents);
    }

    private void sortStudents(String criteria) {
        ObservableList<PlacedStudents> sortedStudents = FXCollections.observableArrayList(filteredPSList);

        switch (criteria) {
            case "ID":
                // Sort by Student ID (S01, S02, etc.)
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.placedStudentIdProperty().get().compareTo(student2.placedStudentIdProperty().get())
                );
                break;
            case "Name":
                // Sort by Name
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.placedStudentNameProperty().get().compareToIgnoreCase(student2.placedStudentNameProperty().get())
                );
                break;
            case "Package":
                // Sort by Package
                FXCollections.sort(sortedStudents, (student1, student2) -> {
                    double package1 = extractPackageValue(student1.placedStudentPackageProperty().get());
                    double package2 = extractPackageValue(student2.placedStudentPackageProperty().get());
                    return Double.compare(package2, package1); // Descending order
                });
                break;

            case "Role":
                // Sort by Status
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.placedStudentRollProperty().get().compareToIgnoreCase(student2.placedStudentRollProperty().get())
                );
                break;
        }

        // Set the sorted list to the ListView
        placedStudentsListView.setItems(sortedStudents);
    }

    private double extractPackageValue(String packageString) {
        // Remove any unwanted characters and trim the string
        String cleanedString = packageString.replaceAll("[^\\d.]", "").trim();

        // Convert to double; if the string is empty, return 0.0
        return cleanedString.isEmpty() ? 0.0 : Double.parseDouble(cleanedString);
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
        placedStudentsListView.setItems(filteredPSList);
    }

    private void onAddPlacedStudent() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_PLACED_STUDENTS);
    }

    public ObservableList<PlacedStudents> getPlacedStudentsObservableList() {
        return placedStudentsObservableList;
    }

    public ListView<PlacedStudents> getPlacedStudentsListView() {
        return placedStudentsListView;
    }


}
