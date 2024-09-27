package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCompanyController implements Initializable {
    public TextField editCompanyName_fld;
    public Label editCompanyId_fld;
    public TextField editCompanyCoordinator_fld;
    public TextField editCompanyPhone_fld;
    public TextField editCompanyEmail_fld;
    public Button editCompanySave_btn;
    public DatePicker editCompanyDate_dt;
    public TextField editCompanyHighestPackage_fld;
    public TextArea editCompanyAddress_fld;
    public Button editCompanyBack_btn;

    private Company company;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editCompanySave_btn.setOnAction(e -> onSave());
        editCompanyBack_btn.setOnAction(e -> onBack());

        createHoverEffect(editCompanySave_btn);
        createHoverEffect(editCompanyBack_btn);
    }

    public void setCompanyData(Company company) {
        this.company = company;

        // Populate fields with student data
        editCompanyName_fld.setText(company.companyNameProperty().getValue());
        editCompanyId_fld.setText(company.companyIdProperty().getValue());
        editCompanyCoordinator_fld.setText(company.companyCoordinatorProperty().getValue());
        editCompanyPhone_fld.setText(company.companyPhoneProperty().getValue());
        editCompanyEmail_fld.setText(company.companyEmailProperty().getValue());
        editCompanyDate_dt.setValue(company.companyDateProperty().getValue());
        editCompanyHighestPackage_fld.setText(company.companyHighestPackageProperty().getValue());
        editCompanyAddress_fld.setText(company.companyAddressProperty().getValue());

    }

    private void onSave() {
        // Get data from input fields
        String name = editCompanyName_fld.getText();
        String Id = editCompanyId_fld.getText();
        String Coordinator = editCompanyCoordinator_fld.getText();
        String Phone = editCompanyPhone_fld.getText();
        String Email = editCompanyEmail_fld.getText();
        java.time.LocalDate Date = editCompanyDate_dt.getValue();
        String HighestPackage = editCompanyHighestPackage_fld.getText();
        String Address = editCompanyAddress_fld.getText();

        if (name.isEmpty() || Id.isEmpty() || Coordinator.isEmpty() || Phone.isEmpty() ||
                Email.isEmpty() || Date == null ) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all required fields.");
            return;
        }



        // Create a new student object with updated data
        Company company = new Company(name, Id, Coordinator , Phone, Email, Date, HighestPackage, Address);

        // Update student in the database
        Model.getInstance().getDatabaseDriver().updateCompany(company);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
        clearFields();

        if (CompanyController.instance != null) {
            CompanyController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
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
        editCompanyName_fld.clear();
        editCompanyId_fld.setText(null);
        editCompanyCoordinator_fld.clear();
        editCompanyPhone_fld.clear();
        editCompanyEmail_fld.clear();
        editCompanyDate_dt.setValue(null);
        editCompanyHighestPackage_fld.clear();
        editCompanyAddress_fld.clear();
        editCompanyHighestPackage_fld.clear();
    }
}
