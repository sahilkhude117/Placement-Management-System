package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.PlacedStudentsCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlacedStudentsController implements Initializable {
    @FXML
    public Button addPlacedStudents_btn;

    public ListView<PlacedStudents> placedStudentsListView;

    public static PlacedStudentsController instance;

    public ObservableList<PlacedStudents> placedStudentsObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        placedStudentsObservableList = FXCollections.observableArrayList();
        placedStudentsListView.setItems(placedStudentsObservableList);
        placedStudentsListView.setCellFactory(placedStudentsListView -> new PlacedStudentsCellFactory());

        addPlacedStudents_btn.setOnAction(event -> onAddPlacedStudent());

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
        placedStudentsListView.setItems(placedStudentsObservableList);
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
