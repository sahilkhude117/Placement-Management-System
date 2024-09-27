package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCompanyController implements Initializable {
    public TextField addCompanyName_fld;
    public TextField addCompanyId_fld;
    public TextField addCompanyCoordinator_fld;
    public TextField addCompanyPhone_fld;
    public TextField addCompanyEmail_fld;
    public Button addCompanySave_btn;
    public DatePicker addCompanyDate_dt;
    public TextField addCompanyHighestPackage_fld;
    public Button addCompanyBack_btn;
    public TextArea addCompanyAddress_fld;

    private ObservableList<Company> companyList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCompanyBack_btn.setOnAction(e -> onBack());
        addCompanySave_btn.setOnAction(e -> onSave());

        createHoverEffect(addCompanyBack_btn);
        createHoverEffect(addCompanySave_btn);

        String nextCompanyId = generateNextCompanyId();
        addCompanyId_fld.setText(nextCompanyId);
    }

    private String generateNextCompanyId() {
        String lastCompanyId = Model.getInstance().getDatabaseDriver().getNextCompanyId();

        if (lastCompanyId == null || lastCompanyId.isEmpty()) {
            return "C1";  // If no ID found, start with "C1"
        }

        // Extract the number part from the last ID (e.g., "C10" -> 10)
        String numberPart = lastCompanyId.substring(1);
        int nextIdNumber = Integer.parseInt(numberPart) + 1;

        // Form the new CompanyID (e.g., "C11")
        return "C" + nextIdNumber;
    }


    public void setCompanyList(ObservableList<Company> CompanyObservableList) {
        this.companyList = CompanyObservableList;
    }

    private void onSave() {
        // Retrieve input values
        String companyName = addCompanyName_fld.getText();
        String companyId = addCompanyId_fld.getText();
        String companyCoordinator = addCompanyCoordinator_fld.getText();
        String companyPhone = addCompanyPhone_fld.getText();
        String companyEmail = addCompanyEmail_fld.getText();
        LocalDate companyDate = addCompanyDate_dt.getValue();
        String companyHighestPackage = addCompanyHighestPackage_fld.getText();
        String companyAddress = addCompanyAddress_fld.getText();

        // Validate input data
        if (companyName.isEmpty() || companyId.isEmpty() || companyCoordinator.isEmpty() || companyPhone.isEmpty() ||
                companyEmail.isEmpty() || companyDate == null ) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all required fields.");
            return;
        }

        boolean  companyExists = Model.getInstance().getDatabaseDriver().companyExists(companyId);
        if (companyExists) {
            showAlert(Alert.AlertType.ERROR, "Error","CompanyID already exists. Try Another Id");
            return;
        }

        // Create a Company object
        Company company = new Company(companyName, companyId, companyCoordinator,  companyPhone,companyEmail,
                companyDate, companyHighestPackage, companyAddress);

        if (companyList != null) {
            companyList.add(company);
        }

        // Add the company to the database
        Model.getInstance().getDatabaseDriver().addCompany(company);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Company added successfully.");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
        clearFields();

        if (CompanyController.instance != null) {
            CompanyController.instance.refresh(); // Refresh the StudentsController
        }
    }

    private void onBack() {
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
        addCompanyName_fld.clear();
        addCompanyId_fld.setText("C");
        addCompanyCoordinator_fld.clear();
        addCompanyPhone_fld.clear();
        addCompanyEmail_fld.clear();
        addCompanyDate_dt.setValue(null);
        addCompanyHighestPackage_fld.clear();
        addCompanyAddress_fld.clear();
    }
}
