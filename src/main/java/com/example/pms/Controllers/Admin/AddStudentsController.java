package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentsController implements Initializable {

    public TextField addStudentsName_fld;
    public TextField addStudentsGpa_fld;
    public TextArea addStudentSkills_fld;
    public TextArea addStudentsAddress_fld;
    public TextField addStudentsPhone_fld;
    public TextField addStudentsEmail_fld;
    public ChoiceBox<String> addStudentsStatus_cb;
    public TextField addStudents10_fld;
    public TextField addStudents12_fld;
    public Button addStudentsAdd_btn;
    public Button addStudentsBack_btn;
    public TextField addStudentsId_fld;
    public ComboBox<String> addStudentsDept_cb;

    // ObservableList to keep track of students (will be passed from StudentsController)
    private ObservableList<Students> studentsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudentsAdd_btn.setOnAction(e -> onSave());
        addStudentsBack_btn.setOnAction(e -> onBack());

        populateStatusChoiceBox();

        String nextStudentId = generateNextStudentId();
        addStudentsId_fld.setText(nextStudentId);
    }

    private String generateNextStudentId() {
        String lastStudentId = Model.getInstance().getDatabaseDriver().getNextStudentId();

        if (lastStudentId == null || lastStudentId.isEmpty()) {
            return "S1";  // If no ID found, start with "S1"
        }

        // Extract the number part from the last ID (e.g., "S10" -> 10)
        String numberPart = lastStudentId.substring(1);
        int nextIdNumber = Integer.parseInt(numberPart) + 1;

        // Form the new StudentID (e.g., "S11")
        return "S" + nextIdNumber;
    }


    public void setStudentsList(ObservableList<Students> studentsObservableList) {
        this.studentsList = studentsObservableList;
    }

    private void populateStatusChoiceBox() {
        addStudentsStatus_cb.setItems(FXCollections.observableArrayList(
                "Placed",
                "Unplaced",
                "Opt to Higher Study"
        ));

        addStudentsDept_cb.setItems(FXCollections.observableArrayList(
                "IT",
                "Comps",
                "Mech",
                "EXTC"
        ));
        addStudentsDept_cb.setEditable(true);


        // Optionally set a default value
        addStudentsDept_cb.setValue("IT");
        addStudentsStatus_cb.setValue("Placed");
    }

    private void onSave() {
        // Retrieve input values
        String name = addStudentsName_fld.getText();
        String dept = addStudentsDept_cb.getValue();
        String gpaText = addStudentsGpa_fld.getText();
        String skills = addStudentSkills_fld.getText();
        String address = addStudentsAddress_fld.getText();
        String phone = addStudentsPhone_fld.getText();
        String email = addStudentsEmail_fld.getText();
        String status = addStudentsStatus_cb.getValue();
        String class10Text = addStudents10_fld.getText();
        String class12Text = addStudents12_fld.getText();
        String id = addStudentsId_fld.getText();

        // Validate input data
        if (name.isEmpty() || dept.isEmpty() || id.isEmpty()  || gpaText.isEmpty() || status == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all required fields.");
            return;
        }

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

        // Check if the username already exists
        boolean studentExists = Model.getInstance().getDatabaseDriver().studentExists(id);
        if (studentExists) {
            showAlert(Alert.AlertType.ERROR, "Error","StudentID already exists. Try Another Id");
            return;
        }

        // Create Students object
        Students student = new Students(name, dept, gpa, skills, address, phone, email, status, class10, class12,id);

        if (status.equals("Placed")) {
            showPlacedStudentAlert(student);
        } else {
            saveStudent(student);
        }
    }

    private void showPlacedStudentAlert(Students student) {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Added to List");
        alert.setHeaderText("This student is marked as Placed.");
        alert.setContentText("Would you like to add this student to the PlacedStudents list?");

        // Get the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                if (studentsList != null) {
                    studentsList.add(student);
                }

                // Save to the database
                Model.getInstance().getDatabaseDriver().addStudent(student);
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
        // Add the new student to the observable list (automatically updates the ListView)
        if (studentsList != null) {
            studentsList.add(student);
        }

        // Save to the database
        Model.getInstance().getDatabaseDriver().addStudent(student);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
        clearFields();

        if (StudentsController.instance != null) {
            StudentsController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack() {
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
        addStudentsName_fld.clear();
        addStudentsDept_cb.setValue("IT");
        addStudentsGpa_fld.clear();
        addStudentSkills_fld.clear();
        addStudentsAddress_fld.clear();
        addStudentsPhone_fld.clear();
        addStudentsEmail_fld.clear();
        addStudentsStatus_cb.setValue("Placed");
        addStudents10_fld.clear();
        addStudents12_fld.clear();
        addStudentsId_fld.setText("S");
    }
}