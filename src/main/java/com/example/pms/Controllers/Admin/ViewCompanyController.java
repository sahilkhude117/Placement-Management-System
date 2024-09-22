package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewCompanyController implements Initializable {
    public Label viewCompanyName1_lbl;
    public Label viewCompanyName2_lbl;
    public Label viewCompanyId_lbl;
    public Label viewCompanyCoordinator_lbl;
    public Label viewCompanyPhone_lbl;
    public Label viewCompanyDate_lbl;
    public Label viewCompanyEmail_lbl;
    public Label viewCompanyHighPackage_lbl;
    public Label viewCompanyAdr_lbl;
    public Button viewCompanyBack_btn;

    private Company company;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewCompanyBack_btn.setOnAction(e -> onBack());
    }

    public void setCompanyData(Company company) {
        this.company = company;

        // Populate fields with student data
       viewCompanyName2_lbl.setText(company.companyNameProperty().getValue());
       viewCompanyName1_lbl.setText(company.companyNameProperty().getValue());
       viewCompanyId_lbl.setText(company.companyIdProperty().getValue());
       viewCompanyCoordinator_lbl.setText(company.companyCoordinatorProperty().getValue());
       viewCompanyPhone_lbl.setText(company.companyPhoneProperty().getValue());
       viewCompanyDate_lbl.setText(company.companyDateProperty().getValue() != null ? company.companyDateProperty().getValue().toString() : "Date not available");
        viewCompanyEmail_lbl.setText(company.companyEmailProperty().getValue());
       viewCompanyHighPackage_lbl.setText(company.companyHighestPackageProperty().getValue());
       viewCompanyAdr_lbl.setText(company.companyAddressProperty().getValue());

    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
    }
}
