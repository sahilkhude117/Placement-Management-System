package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;



public class PlacedStudentsCellController implements Initializable {
    public Label PlacedStudents_SrNo_lbl;
    public Label PlacedStudents_Name_lbl;
    public Label PlacedStudents_Dept_lbl;
    public Label PlacedStudents_Lpa_lbl;
    public Label PlacedStudents_Phone_lbl;
    public Label PlacedStudent_Roll_lbl;
    public Button PlacedStudents_View_btn;
    public Button PlacedStudents_Edit_btn;
    public Button PlacedStudents_Delete_btn;
    public Label PlacedStudents_Company_lbl;

    private PlacedStudents placedStudents;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (placedStudents != null) {
            PlacedStudents_Name_lbl.setText(placedStudents.placedStudentNameProperty().getValue());
            PlacedStudents_Dept_lbl.setText(placedStudents.placedStudentDeptProperty().getValue());
            PlacedStudents_Lpa_lbl.setText(String.valueOf(placedStudents.placedStudentPackageProperty().getValue()));
            PlacedStudents_Phone_lbl.setText(placedStudents.placedStudentPhoneProperty().getValue());
            PlacedStudents_Company_lbl.setText(placedStudents.placedStudentCompanyProperty().getValue());
            PlacedStudent_Roll_lbl.setText(placedStudents.placedStudentRollProperty().getValue());
            PlacedStudents_SrNo_lbl.setText(placedStudents.placedStudentIdProperty().getValue());
        }

        // Set up button actions
        PlacedStudents_View_btn.setOnAction(event -> onView(placedStudents));
        PlacedStudents_Edit_btn.setOnAction(event -> onEdit());
        PlacedStudents_Delete_btn.setOnAction(event -> onDelete());

    }

    public PlacedStudentsCellController(PlacedStudents placedStudents) {
        this.placedStudents = placedStudents;
    }

    public void setPlacedStudents(PlacedStudents placedStudents) {
        this.placedStudents = placedStudents;
        // Update UI elements with student data
        initialize(null, null);
    }

    private void onView(PlacedStudents selectedPlacedStudents) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.VIEW_PLACED_STUDENTS);
        Model.getInstance().getViewFactory().updateViewPlacedStudentsData(selectedPlacedStudents);
    }

    private void onEdit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.EDIT_PLACED_STUDENTS);

        EditPlacedStudentsController editplacedStudentsController = (EditPlacedStudentsController) Model.getInstance()
                .getViewFactory()
                .getController(AdminMenuOptions.EDIT_PLACED_STUDENTS);

        if (editplacedStudentsController != null) {
            // Pass the selected student to the EditStudentsController
            editplacedStudentsController.setPlacedStudentData(placedStudents);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Edit page is not available.");
        }
    }

    private void onDelete() {
        // Get the student ID to delete
        String studentId = placedStudents.placedStudentIdProperty().getValue();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this student?");
        confirmationAlert.setContentText("This action cannot be undone.");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the method to delete the student from the database
                Model.getInstance().getDatabaseDriver().deletePlacedStudent(studentId);

                // Find the StudentsController and remove the student from the ListView
                PlacedStudentsController placedStudentsController = (PlacedStudentsController) Model.getInstance()
                        .getViewFactory()
                        .getController(AdminMenuOptions.PLACED_STUDENTS);

                if (placedStudentsController != null) {
                    ListView<PlacedStudents> listView = placedStudentsController.getPlacedStudentsListView();
                    if (listView != null) {
                        listView.getItems().remove(placedStudents);
                    }
                }



                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
            }
        });

        if (PlacedStudentsController.instance != null) {
            PlacedStudentsController.instance.refresh(); // Refresh the StudentsController
        }

        if (DashboardController.instance != null) {
            DashboardController.instance.refresh();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
