package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;



public class CompanyCellController implements Initializable {
    public Label Company_SrNo_lbl;
    public Label Company_Name_lbl;
    public Label Company_Cord_lbl;
    public Label Company_Phone_lbl;
    public Button Company_View_btn;
    public Button Company_Edit_btn;
    public Button Company_Delete_btn;
    public Label Company_email_lbl;
    public AnchorPane CompanyCell_ap;

    private Company company;

    public CompanyCellController(Company company) {
        this.company = company;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(company != null) {
            Company_SrNo_lbl.setText(company.companyIdProperty().getValue());
            Company_Name_lbl.setText(company.companyNameProperty().getValue());
            Company_Cord_lbl.setText(company.companyCoordinatorProperty().getValue());
            Company_Phone_lbl.setText(company.companyPhoneProperty().getValue());
            Company_email_lbl.setText(company.companyEmailProperty().getValue());
        }

        createHoverEffect(Company_Delete_btn);
        createHoverEffect(Company_Edit_btn);
        createHoverEffect(Company_View_btn);
        createHoverEffect(CompanyCell_ap);

        Company_View_btn.setOnAction(e -> onView(company));
        Company_Edit_btn.setOnAction(e -> onEdit());
        Company_Delete_btn.setOnAction(e -> onDelete());
        CompanyCell_ap.setOnMouseClicked(e -> onView(company));
    }

    public void setCompany(Company company) {
        this.company = company;
        // Update UI elements with student data
        initialize(null, null);
    }

    private void onView(Company company) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.VIEW_COMPANIES);
        Model.getInstance().getViewFactory().updateViewCompanyData(company);
    }

    private void onEdit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.EDIT_COMPANIES);

        EditCompanyController editcompanyController = (EditCompanyController) Model.getInstance()
                .getViewFactory()
                .getController(AdminMenuOptions.EDIT_COMPANIES);

        if (editcompanyController != null) {
            // Pass the selected student to the EditStudentsController
            editcompanyController.setCompanyData(company);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Edit page is not available.");
        }
    }

    private void onDelete() {

        String companyId = company.companyIdProperty().getValue();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this student?");
        confirmationAlert.setContentText("This action cannot be undone.");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the method to delete the student from the database
                Model.getInstance().getDatabaseDriver().deleteCompany(companyId);

                // Find the StudentsController and remove the student from the ListView
                CompanyController companyController = (CompanyController) Model.getInstance()
                        .getViewFactory()
                        .getController(AdminMenuOptions.COMPANY);

                if (companyController != null) {
                    ListView<Company> listView = companyController.getCompaniesListView();
                    if (listView != null) {
                        listView.getItems().remove(company);
                    }
                }

                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Success", "Company deleted successfully.");
            }
        });

        if (CompanyController.instance != null) {
            CompanyController.instance.refresh(); // Refresh the StudentsController
        }
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

    public void createHoverEffect(AnchorPane ap) {
        // Create scale transition for the button
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), ap);
        scaleIn.setToX(1.025);
        scaleIn.setToY(1.025);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), ap);
        scaleOut.setToX(1);
        scaleOut.setToY(1);

        // Set hover event listeners
        ap.setOnMouseEntered(e -> scaleIn.playFromStart());
        ap.setOnMouseExited(e -> scaleOut.playFromStart());
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
