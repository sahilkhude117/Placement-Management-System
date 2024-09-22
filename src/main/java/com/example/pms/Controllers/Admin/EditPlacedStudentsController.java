package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.AdminMenuOptions;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditPlacedStudentsController implements Initializable {
    public TextField editPSName_fld;
    public ComboBox<String> editPSDept_fld;
    public TextField editPSCompany_fld;
    public TextField editPSRoll_fld;
    public TextField editPSPackage_fld;
    public TextField editPSEmail_fld;
    public Button editPSSave_btn;
    public DatePicker editPSDate_dt;
    public TextField editPSPhone_fld;
    public TextArea editPSAddress_fld;
    public Button editPSBack_btn;
    public Label editPSId_fld1;

    private PlacedStudents placedStudent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           editPSSave_btn.setOnAction(e -> onSave());
           editPSBack_btn.setOnAction(e -> onBack());

           populateComboBox();
           setupCompanyAutocomplete();
           setupRoleAutocomplete();
           setupPackageAutocomplete();
    }

    private void setupCompanyAutocomplete() {
        List<String> companyNames = Model.getInstance().getDatabaseDriver().getAllCompanyNames();  // Fetch company names
        TextFields.bindAutoCompletion(editPSCompany_fld, companyNames);  // Bind company names to autocomplete
    }

    private void setupRoleAutocomplete() {
        List<String> roles = Model.getInstance().getDatabaseDriver().getAllRoles();
        TextFields.bindAutoCompletion(editPSRoll_fld, roles);
    }

    private void setupPackageAutocomplete() {
        List<String> packages = Model.getInstance().getDatabaseDriver().getAllPackages();
        TextFields.bindAutoCompletion(editPSPackage_fld, packages);
    }

     private void populateComboBox(){
         editPSDept_fld.setItems(FXCollections.observableArrayList(
                 "IT",
                 "Comps",
                 "Mech",
                 "EXTC"
         ));
         editPSDept_fld.setEditable(true);
         editPSDept_fld.setValue("IT");
     }

    public void setPlacedStudentData(PlacedStudents placedStudents) {
        this.placedStudent = placedStudents;

        // Populate fields with student data
        editPSName_fld.setText(placedStudents.placedStudentNameProperty().getValue());
        editPSDept_fld.setValue(placedStudents.placedStudentDeptProperty().getValue());
        editPSCompany_fld.setText(placedStudents.placedStudentCompanyProperty().getValue());
        editPSRoll_fld.setText(placedStudents.placedStudentRollProperty().getValue());
        editPSPackage_fld.setText(placedStudents.placedStudentPackageProperty().getValue());
        editPSEmail_fld.setText(placedStudents.placedStudentEmailProperty().getValue());
        editPSDate_dt.setValue(placedStudents.placedStudentDateProperty().getValue());
        editPSPhone_fld.setText(placedStudents.placedStudentPhoneProperty().getValue());
        editPSAddress_fld.setText(placedStudents.placedStudentAddressProperty().getValue());
        editPSId_fld1.setText(placedStudents.placedStudentIdProperty().getValue());
    }

    private void onSave() {
        // Get data from input fields
        String name = editPSName_fld.getText();
        String dept = editPSDept_fld.getValue();
        String phone = editPSPhone_fld.getText();
        String role = editPSRoll_fld.getText();
        String packageValue = editPSPackage_fld.getText();
        String company = editPSCompany_fld.getText();
        String email = editPSEmail_fld.getText();
        java.time.LocalDate date = editPSDate_dt.getValue();
        String address = editPSAddress_fld.getText();
        String Id = editPSId_fld1.getText();

        if (name.isEmpty() || dept.isEmpty() || company.isEmpty() || role.isEmpty() || packageValue.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all required fields.");
            return;
        }


        // Create a new student object with updated data
        PlacedStudents placedStudent = new PlacedStudents(name, dept, phone, role, packageValue, company, email, date, address,Id);

        // Update student in the database
        Model.getInstance().getDatabaseDriver().updatePlacedStudent(placedStudent);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
        clearFields();

        if (PlacedStudentsController.instance != null) {
            PlacedStudentsController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        editPSName_fld.clear();
        editPSDept_fld.setValue(null);
        editPSPhone_fld.clear();
        editPSPackage_fld.clear();
        editPSCompany_fld.clear();
        editPSEmail_fld.clear();
        editPSEmail_fld.clear();
        editPSAddress_fld.clear();
        editPSId_fld1.setText(null);
    }
}
