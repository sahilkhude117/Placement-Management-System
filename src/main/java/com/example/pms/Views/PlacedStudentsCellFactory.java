package com.example.pms.Views;

import com.example.pms.Controllers.Admin.PlacedStudentsCellController;
import com.example.pms.Models.PlacedStudents;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PlacedStudentsCellFactory extends ListCell<PlacedStudents> {

    @Override
    protected void updateItem(PlacedStudents placedStudents, boolean empty) {
        super.updateItem(placedStudents, empty);

        if (empty || placedStudents == null) {
            // If empty, reset the cell content
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Load the FXML file for the student cell
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/PlacedStudentsCell.fxml"));

                // Create the controller and set the student data
                PlacedStudentsCellController controller = new PlacedStudentsCellController(placedStudents);
                loader.setController(controller);

                // Load the FXML and set it as the graphic
                AnchorPane placedStudentCell = loader.load();
                setGraphic(placedStudentCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors gracefully by resetting the cell
                setText("Error loading cell");
                setGraphic(null);
            }
        }
    }
}
