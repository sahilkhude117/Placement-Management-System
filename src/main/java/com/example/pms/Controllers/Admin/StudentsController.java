package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.StudentsCellFactory;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    @FXML
    public Button addStudents_btn;

    @FXML
    public ListView<Students> studentsListView;
    public static StudentsController instance;

    public ObservableList<Students> studentsObservableList; // ObservableList to hold student data
    public MenuButton sortByDeptStudents_mb;

    private FilteredList<Students> filteredStudentsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instance = this;
        studentsObservableList = FXCollections.observableArrayList();
        studentsListView.setItems(studentsObservableList);
        studentsListView.setCellFactory(studentListView -> new StudentsCellFactory());
        addStudents_btn.setOnAction(event -> onAddStudents());

        createHoverEffect(addStudents_btn);
        createHoverEffect(sortByDeptStudents_mb);
        refresh();
        populateDepartmentMenuButton();
    }

    private void populateDepartmentMenuButton() {

        sortByDeptStudents_mb.getItems().clear();

        // Create MenuItems for each department
        MenuItem it = new MenuItem("IT");
        MenuItem mech = new MenuItem("MECH");
        MenuItem comps = new MenuItem("COMPS");
        MenuItem extc = new MenuItem("EXTC");


        // Add MenuItems to the MenuButton for departments
        sortByDeptStudents_mb.getItems().addAll(it, mech, comps, extc);

        // Optionally: Set an action for when an item is selected
        it.setOnAction(event -> handleDepartmentSelection("IT"));
        mech.setOnAction(event -> handleDepartmentSelection("MECH"));
        comps.setOnAction(event -> handleDepartmentSelection("COMPS"));
        extc.setOnAction(event -> handleDepartmentSelection("EXTC"));


        // Create MenuItems for sorting options
        MenuItem sortById = new MenuItem("Sort by ID");
        MenuItem sortByName = new MenuItem("Sort by Name");
        MenuItem sortByGpa = new MenuItem("Sort by GPA");
        MenuItem sortByStatus = new MenuItem("Sort by Status");
        MenuItem def = new MenuItem("Default");

        // Add sorting options to the MenuButton
        sortByDeptStudents_mb.getItems().addAll(sortById, sortByName, sortByGpa, sortByStatus,def);

        // Set actions for each sorting option
        sortById.setOnAction(event -> sortStudents("ID"));
        sortByName.setOnAction(event -> sortStudents("Name"));
        sortByGpa.setOnAction(event -> sortStudents("GPA"));
        sortByStatus.setOnAction(event -> sortStudents("Status"));
        def.setOnAction(event -> handleDepartmentSelection("Default"));
    }


    private void handleDepartmentSelection(String department) {
        sortByDeptStudents_mb.setText(department);

        if (department.equals("Default")) {
            // Show all students if "ALL" is selected
            sortByDeptStudents_mb.setText("Sort By");
            filteredStudentsList.setPredicate(student -> true);
        } else {
            // Filter students by the selected department
            filteredStudentsList.setPredicate(student -> student.studentDeptProperty().get().equals(department));
        }

        // Convert FilteredList to ObservableList
        ObservableList<Students> sortedStudents = FXCollections.observableArrayList(filteredStudentsList);

        // Sort the students by name (or any other attribute)
        FXCollections.sort(sortedStudents, (student1, student2) ->
                student1.studentNameProperty().get().compareToIgnoreCase(student2.studentNameProperty().get())
        );

        // Set the sorted list to the ListView
        studentsListView.setItems(sortedStudents);
    }

    private void sortStudents(String criteria) {
        ObservableList<Students> sortedStudents = FXCollections.observableArrayList(filteredStudentsList);

        switch (criteria) {
            case "ID":
                // Sort by Student ID (S01, S02, etc.)
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.studentIDProperty().get().compareTo(student2.studentIDProperty().get())
                );
                break;
            case "Name":
                // Sort by Name
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.studentNameProperty().get().compareToIgnoreCase(student2.studentNameProperty().get())
                );
                break;
            case "GPA":
                // Sort by GPA (Highest GPA first)
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        Double.compare(student2.studentGpaProperty().get(), student1.studentGpaProperty().get())
                );
                break;
            case "Status":
                // Sort by Status
                FXCollections.sort(sortedStudents, (student1, student2) ->
                        student1.studentStatusProperty().get().compareToIgnoreCase(student2.studentStatusProperty().get())
                );
                break;
        }

        // Set the sorted list to the ListView
        studentsListView.setItems(sortedStudents);
    }




    public void refresh() {
        // Retrieve updated data from the database
        List<Students> retrievedStudents = Model.getInstance().getDatabaseDriver().getAllStudents();

        // Clear the current data in the ObservableList
        studentsObservableList.clear();

        // Add the updated data to the ObservableList
        studentsObservableList.addAll(retrievedStudents);

        filteredStudentsList = new FilteredList<>(studentsObservableList, student -> true);

        // Refresh the ListView
        studentsListView.setItems(filteredStudentsList);
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


    private void onAddStudents() {
        // Navigate to AddStudents view
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_STUDENTS);
    }


    public ObservableList<Students> getStudentsObservableList() {
        return studentsObservableList;
    }

    public ListView<Students> getStudentsListView() {
        return studentsListView;
    }
}