package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddPlacedStudentsController implements Initializable {

    public TextField addPSName_fld;
    public ComboBox<String> addPSDept_fld;
    public TextField addPSCompany_fld;
    public TextField addPSRoll_fld;
    public TextField addPSPackage_fld;
    public TextField addPSEmail_fld;
    public DatePicker addPSDate_dt;
    public TextField addPSPhone_fld;
    public TextArea addPSAddress_fld;
    public Button addPSSave_btn;
    public Button addPSBack_btn;
    public TextField addPSId_fld;

    private ObservableList<PlacedStudents> placedStudentsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPSSave_btn.setOnAction(e -> onSave());
        addPSBack_btn.setOnAction(e -> onBack());

        createHoverEffect(addPSBack_btn);
        createHoverEffect(addPSSave_btn);

        populateComboBox();
        setupStudentIdAutocomplete();
        setupCompanyAutocomplete();
        setupRoleAutocomplete();
        setupPackageAutocomplete();


    }


    private void setupCompanyAutocomplete() {
        List<String> companyNames = Model.getInstance().getDatabaseDriver().getAllCompanyNames();  // Fetch company names
        TextFields.bindAutoCompletion(addPSCompany_fld, companyNames);  // Bind company names to autocomplete
    }

    private void setupRoleAutocomplete() {
        List<String> roles = Model.getInstance().getDatabaseDriver().getAllRoles();
        TextFields.bindAutoCompletion(addPSRoll_fld, roles);
    }

    private void setupPackageAutocomplete() {
        List<String> packages = Model.getInstance().getDatabaseDriver().getAllPackages();
        TextFields.bindAutoCompletion(addPSPackage_fld, packages);
    }


    private void setupStudentIdAutocomplete() {
        List<String> studentIds = Model.getInstance().getDatabaseDriver().getStudentsByStatus("Placed");
        TextFields.bindAutoCompletion(addPSId_fld, studentIds);

        // Fetch data when user selects an autocomplete suggestion or types in a valid ID and moves away from the field
        addPSId_fld.setOnAction(event -> {
            String id = addPSId_fld.getText();
            if (!id.isEmpty()) {
                checkAndFillStudentDetails(id);
            }
        });

        // Add focus listener to fetch data when user moves out of the text field
        addPSId_fld.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                String id = addPSId_fld.getText();
                if (!id.isEmpty()) {
                    checkAndFillStudentDetails(id);
                }
            }
        });
    }

    private void checkAndFillStudentDetails(String studentId) {
        List<String> studentIds = Model.getInstance().getDatabaseDriver().getStudentsByStatus("Placed");

        if (!studentIds.contains(studentId)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Warning",
                    "Student Not Found. You will be redirected to add the student.");
            Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_STUDENTS);
            clearFields(); // Clear fields if ID is invalid
            return;
        }

        Students student = Model.getInstance().getDatabaseDriver().getStudentDetailsById(studentId);
        if (student != null) {
            populateFields(student);
        } else {
            clearFields();
            showAlert(Alert.AlertType.ERROR, "Error", "No student found with this ID");
        }
    }

    private void populateComboBox(){
        addPSDept_fld.setItems(FXCollections.observableArrayList(
                "IT",
                "Comps",
                "Mech",
                "EXTC"
        ));
        addPSDept_fld.setEditable(true);
        addPSDept_fld.setValue("IT");
    }

    private void populateFields(Students student) {
        addPSName_fld.setText(student.studentNameProperty().getValue());
        addPSDept_fld.setValue(student.studentDeptProperty().getValue());
        addPSPhone_fld.setText(student.studentPhoneProperty().getValue());
        addPSEmail_fld.setText(student.studentEmailProperty().getValue());
        addPSAddress_fld.setText(student.studentAddressProperty().getValue());
    }

    public void populateFields1(Students student) {
        addPSName_fld.setText(student.studentNameProperty().getValue());
        addPSDept_fld.setValue(student.studentDeptProperty().getValue());
        addPSPhone_fld.setText(student.studentPhoneProperty().getValue());
        addPSEmail_fld.setText(student.studentEmailProperty().getValue());
        addPSAddress_fld.setText(student.studentAddressProperty().getValue());
        addPSId_fld.setText(student.studentIDProperty().getValue()); // Assuming there's a getId method
    }

    private void onSave() {
        // Retrieve input values
        String name = addPSName_fld.getText();
        String dept = addPSDept_fld.getValue();
        String phone = addPSPhone_fld.getText();
        String role = addPSRoll_fld.getText();
        String packageAmount = addPSPackage_fld.getText();
        String company = addPSCompany_fld.getText();
        String email = addPSEmail_fld.getText();
        LocalDate placementDate = addPSDate_dt.getValue();
        String address = addPSAddress_fld.getText();
        String id = addPSId_fld.getText();

        // Validate input data
        if (name.isEmpty() || dept.isEmpty() || company.isEmpty() || role.isEmpty()  || email.isEmpty() || placementDate == null || phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all required fields.");
            return;
        }


        List<String> studentIds = Model.getInstance().getDatabaseDriver().getStudentsByStatus("Placed");

        if (!studentIds.contains(id)) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setHeaderText("Student Not Found, Add To Students First");
            confirmationAlert.setContentText("You will be redirected to students page");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                   Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_STUDENTS);
                }
            });
            return;
        }

        boolean placedStudentExists = Model.getInstance().getDatabaseDriver().placedStudentExists(id);
        if (placedStudentExists) {
            showAlert(Alert.AlertType.ERROR, "Error","StudentID already exists. Try Another Id");
            return;
        }

        PlacedStudents placedStudent = new PlacedStudents(name,dept,phone,role,packageAmount,company,email,placementDate,address,id);

        if (placedStudentsList != null) {
            placedStudentsList.add(placedStudent);
        }

        // Add the placed student to the database
        Model.getInstance().getDatabaseDriver().addPlacedStudent(placedStudent);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Placed student added successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
        clearFields();

        if (PlacedStudentsController.instance != null) {
            PlacedStudentsController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
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


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        addPSName_fld.clear();
        addPSDept_fld.setValue(null);
        addPSCompany_fld.clear();
        addPSRoll_fld.clear();
        addPSPackage_fld.clear();
        addPSEmail_fld.clear();
        addPSDate_dt.setValue(null);
        addPSPhone_fld.clear();
        addPSAddress_fld.clear();
        addPSId_fld.setText("S");
    }
}

