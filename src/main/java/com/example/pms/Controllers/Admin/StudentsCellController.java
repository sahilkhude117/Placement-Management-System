package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsCellController implements Initializable {

    public Label Students_SrNo_lbl;
    public Label Students_Name_lbl;
    public Label Students_Dept_lbl;
    public Label Students_Gpa_lbl;
    public Label Students_Phone_lbl;
    public Label Student_Status_lbl;
    public Button Students_View_lbl;
    public Button Students_Delete_btn;
    public Button Students_Edit_btn;

    private Students students;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up initial data for the labels and buttons
        if (students != null) {
            Students_Name_lbl.setText(students.studentNameProperty().getValue());
            Students_Dept_lbl.setText(students.studentDeptProperty().getValue());
            Students_Gpa_lbl.setText(String.valueOf(students.studentGpaProperty().getValue()));
            Students_Phone_lbl.setText(students.studentPhoneProperty().getValue());
            Student_Status_lbl.setText(students.studentStatusProperty().getValue());
            Students_SrNo_lbl.setText(students.studentIDProperty().getValue());

            // Set the status label and color based on placement status
            setStudentStatus(students.studentStatusProperty().getValue());
        }

        // Set up button actions
        Students_View_lbl.setOnAction(event -> onView(students));
        Students_Edit_btn.setOnAction(event -> onEdit());
        Students_Delete_btn.setOnAction(event -> onDelete());
    }

    public StudentsCellController(Students students) {
        this.students = students;
    }

    public void setStudents(Students students) {
        this.students = students;
        // Update UI elements with student data
        initialize(null, null);
    }

    private void setStudentStatus(String status) {
        Student_Status_lbl.setText(status);

        switch (status) {
            case "Placed":
                Student_Status_lbl.setTextFill(Color.GREEN);
                break;
            case "Unplaced":
                Student_Status_lbl.setTextFill(Color.RED);
                break;
            case "Opt for Higher Study":
                Student_Status_lbl.setTextFill(Color.GRAY);
                break;
            default:
                Student_Status_lbl.setTextFill(Color.BLACK); // Default color
                break;
        }
    }




    private void onView(Students selectedStudent) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.VIEW_STUDENTS);
        Model.getInstance().getViewFactory().updateViewStudentsData(selectedStudent);
    }

    private void onEdit() {
        // Navigate to the edit students page
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.EDIT_STUDENTS);

        EditStudentsController editController = (EditStudentsController) Model.getInstance()
                .getViewFactory()
                .getController(AdminMenuOptions.EDIT_STUDENTS);

        if (editController != null) {
            // Pass the selected student to the EditStudentsController
            editController.setStudentData(students);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Edit page is not available.");
        }
    }

    private void onDelete() {
        // Get the student ID to delete
        String studentId = students.studentIDProperty().getValue();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this student?");
        confirmationAlert.setContentText("This action cannot be undone.");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the method to delete the student from the database
                Model.getInstance().getDatabaseDriver().deleteStudent(studentId);

                // Find the StudentsController and remove the student from the ListView
                StudentsController studentsController = (StudentsController) Model.getInstance()
                        .getViewFactory()
                        .getController(AdminMenuOptions.STUDENTS);

                if (studentsController != null) {
                    ListView<Students> listView = studentsController.getStudentsListView();
                    if (listView != null) {
                        listView.getItems().remove(students);
                    }
                }

                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
            }
        });

        if (StudentsController.instance != null) {
            StudentsController.instance.refresh(); // Refresh the StudentsController
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
