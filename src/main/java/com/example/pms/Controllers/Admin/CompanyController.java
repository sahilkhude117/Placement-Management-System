package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.CompanyCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyController implements Initializable {
    @FXML
    public Button addCompany_btn;

    @FXML
    public ListView<Company> companyListView;
    public static CompanyController instance;

    public ObservableList<Company> companyObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        companyObservableList = FXCollections.observableArrayList();
        companyListView.setItems(companyObservableList);
        companyListView.setCellFactory(companyListView -> new CompanyCellFactory());

        addCompany_btn.setOnAction(event -> onAddCompany());

        refresh();
    }

    public void refresh() {
        // Retrieve updated data from the database
        List<Company> retrievedStudents = Model.getInstance().getDatabaseDriver().getAllCompanies();

        // Clear the current data in the ObservableList
        companyObservableList.clear();

        // Add the updated data to the ObservableList
        companyObservableList.addAll(retrievedStudents);

        // Refresh the ListView
        companyListView.setItems(companyObservableList);
    }

    private void onAddCompany() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_COMPANIES);
    }

    public ObservableList<Company> getCompaniesObservableList() {
        return companyObservableList;
    }

    public ListView<Company> getCompaniesListView() {
        return companyListView;
    }
}
