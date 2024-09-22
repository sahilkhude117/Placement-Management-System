package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EditStudentsController implements Initializable {
    public TextField editStudentsName_fld;
    public ComboBox<String> editStudentsDept_fld;
    public TextField editStudentsGpa_fld;
    public TextArea editStudentSkills_fld;
    public TextArea editStudentsAddress_fld;
    public TextField editStudentsPhone_fld;
    public TextField editStudentsEmail_fld;
    public ChoiceBox<String> editStudentsStatus_cb;
    public TextField editStudents10_fld;
    public TextField editStudents12_fld;
    public Button editStudentsAdd_btn;
    public Button editStudentsBack_btn;
    public Label editStudentsId_fld1;

    private Students student;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ChoiceBox fields
        populateStatusComboBox();

        // Add Button event listener
        editStudentsAdd_btn.setOnAction(e -> onSave());
        editStudentsBack_btn.setOnAction(event -> onBack());
    }

    private void populateStatusComboBox() {
        editStudentsStatus_cb.setItems(FXCollections.observableArrayList(
                "Placed",
                "Unplaced",
                "Opt to Higher Study"
        ));


        editStudentsDept_fld.setItems(FXCollections.observableArrayList(
                "IT",
                "Computer Science",
                "Mechanical",
                "EXTC"
        ));

        editStudentsDept_fld.setEditable(true);
    }

    // This method will be called after loading the FXML to set the student's data
    public void setStudentData(Students student) {
        this.student = student;

        // Populate fields with student data
        editStudentsName_fld.setText(student.studentNameProperty().getValue());
        editStudentsDept_fld.setValue(student.studentDeptProperty().getValue());
        editStudentsGpa_fld.setText(String.valueOf(student.studentGpaProperty().getValue()));
        editStudentSkills_fld.setText(student.studentSkillsProperty().getValue());
        editStudentsAddress_fld.setText(student.studentAddressProperty().getValue());
        editStudentsPhone_fld.setText(student.studentPhoneProperty().getValue());
        editStudentsEmail_fld.setText(student.studentEmailProperty().getValue());
        editStudentsStatus_cb.setValue(student.studentStatusProperty().getValue());
        editStudents10_fld.setText(String.valueOf(student.studentClass10Property().getValue()));
        editStudents12_fld.setText(String.valueOf(student.studentClass12Property().getValue()));
        editStudentsId_fld1.setText(student.studentIDProperty().getValue());
    }

    private void onSave() {
        // Get data from input fields
        String name = editStudentsName_fld.getText();
        String dept = editStudentsDept_fld.getValue();
        String gpaText = editStudentsGpa_fld.getText();
        String skills = editStudentSkills_fld.getText();
        String address = editStudentsAddress_fld.getText();
        String phone = editStudentsPhone_fld.getText();
        String email = editStudentsEmail_fld.getText();
        String status = editStudentsStatus_cb.getValue();
        String class10Text = editStudents10_fld.getText();
        String class12Text = editStudents12_fld.getText();
        String id = editStudentsId_fld1.getText();

        // Parse GPA and Class 10/12 values with error handling
        double gpa;
        double class10;
        double class12;
        try {
            gpa = Double.parseDouble(gpaText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "GPA must be a number.");
            return;
        }

        try {
            class10 = Double.parseDouble(class10Text.isEmpty() ? "0" : class10Text);
            class12 = Double.parseDouble(class12Text.isEmpty() ? "0" : class12Text);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Class 10 and Class 12 must be numbers.");
            return;
        }
        

        // Create a new student object with updated data
        Students student = new Students(name, dept, gpa, skills, address, phone, email, status, class10, class12, id);

        if (status.equals("Placed")) {
            if ( Model.getInstance().getDatabaseDriver().placedStudentExists(id)){
                saveStudent(student);
            } else{
                showPlacedStudentAlert(student);
            }
        } else {
            saveStudent(student);
        }
    }

    private void showPlacedStudentAlert(Students student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Added to List");
        alert.setHeaderText("This student is marked as Placed.");
        alert.setContentText("Would you like to add this student to the PlacedStudents list?");

        // Get the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                // Save to the database
                Model.getInstance().getDatabaseDriver().updateStudent(student);
                clearFields();
                if (StudentsController.instance != null) {
                    StudentsController.instance.refresh(); // Refresh the StudentsController
                }

                Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_PLACED_STUDENTS);

                //autofill fields
                AddPlacedStudentsController addPlacedStudentsController = (AddPlacedStudentsController) Model.getInstance()
                        .getViewFactory()
                        .getController(AdminMenuOptions.ADD_PLACED_STUDENTS);

                if (addPlacedStudentsController != null) {
                    // Pass the selected student to the EditStudentsController
                    addPlacedStudentsController.populateFields1(student);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Add page is not available.");
                }
            } else {
                saveStudent(student);
            }
        });
    }

    private void saveStudent(Students student) {

        // Save to the database
        Model.getInstance().getDatabaseDriver().updateStudent(student);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
        clearFields();

        if (StudentsController.instance != null) {
            StudentsController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        editStudentsName_fld.clear();
        editStudentsDept_fld.setValue(null);
        editStudentsGpa_fld.clear();
        editStudentSkills_fld.clear();
        editStudentsAddress_fld.clear();
        editStudentsPhone_fld.clear();
        editStudentsEmail_fld.clear();
        editStudentsStatus_cb.setValue(null);
        editStudents10_fld.clear();
        editStudents12_fld.clear();
        editStudentsId_fld1.setText(null);
    }
}