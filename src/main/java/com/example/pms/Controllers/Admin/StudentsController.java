package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.StudentsCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instance = this;
        studentsObservableList = FXCollections.observableArrayList();
        studentsListView.setItems(studentsObservableList);
        studentsListView.setCellFactory(studentListView -> new StudentsCellFactory());

        addStudents_btn.setOnAction(event -> onAddStudents());

        refresh();
    }

    public void refresh() {
        // Retrieve updated data from the database
        List<Students> retrievedStudents = Model.getInstance().getDatabaseDriver().getAllStudents();

        // Clear the current data in the ObservableList
        studentsObservableList.clear();

        // Add the updated data to the ObservableList
        studentsObservableList.addAll(retrievedStudents);

        // Refresh the ListView
        studentsListView.setItems(studentsObservableList);
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