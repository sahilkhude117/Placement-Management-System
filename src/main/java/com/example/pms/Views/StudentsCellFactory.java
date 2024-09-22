package com.example.pms.Views;

import com.example.pms.Controllers.Admin.StudentsCellController;
import com.example.pms.Models.Students;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentsCellFactory extends ListCell<Students> {

    @Override
    protected void updateItem(Students students, boolean empty) {
        super.updateItem(students, empty);

        if (empty || students == null) {
            // If empty, reset the cell content
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Load the FXML file for the student cell
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/StudentsCell.fxml"));

                // Create the controller and set the student data
                StudentsCellController controller = new StudentsCellController(students);
                loader.setController(controller);

                // Load the FXML and set it as the graphic
                AnchorPane studentCell = loader.load();
                setGraphic(studentCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors gracefully by resetting the cell
                setText("Error loading cell");
                setGraphic(null);
            }
        }
    }
}