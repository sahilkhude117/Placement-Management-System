package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Company;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import com.example.pms.Views.CompanyCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyController implements Initializable {
    @FXML
    public Button addCompany_btn;

    @FXML
    public ListView<Company> companyListView;

    @FXML
    public MenuButton sortByCompany_mb; // MenuButton for sorting

    public static CompanyController instance;

    public ObservableList<Company> companyObservableList;
    private FilteredList<Company> filteredCompanyList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        companyObservableList = FXCollections.observableArrayList();
        filteredCompanyList = new FilteredList<>(companyObservableList, company -> true);
        companyListView.setItems(filteredCompanyList);
        companyListView.setCellFactory(companyListView -> new CompanyCellFactory());

        addCompany_btn.setOnAction(event -> onAddCompany());

        // Populate the sort menu
        populateSortMenu();

        refresh();
    }

    private void populateSortMenu() {

        sortByCompany_mb.getItems().clear();

        MenuItem sortById = new MenuItem("Sort by ID");
        MenuItem sortByName = new MenuItem("Sort by Name");
        MenuItem defaultSort = new MenuItem("Default");

        sortByCompany_mb.getItems().addAll(sortById, sortByName, defaultSort);

        sortById.setOnAction(event -> sortCompanies("ID"));
        sortByName.setOnAction(event -> sortCompanies("Name"));
        defaultSort.setOnAction(event -> refresh());
    }

    private void sortCompanies(String criteria) {
        switch (criteria) {
            case "ID":
                // Sort by Company ID
                companyObservableList.sort((c1, c2) -> c1.companyIdProperty().get().compareTo(c2.companyIdProperty().get()));
                break;
            case "Name":
                // Sort by Company Name
                companyObservableList.sort((c1, c2) -> c1.companyNameProperty().get().compareToIgnoreCase(c2.companyNameProperty().get()));
                break;
        }
        companyListView.setItems(filteredCompanyList); // Update the ListView with sorted data
    }

    public void refresh() {
        // Retrieve updated data from the database
        List<Company> retrievedCompanies = Model.getInstance().getDatabaseDriver().getAllCompanies();

        // Clear the current data in the ObservableList
        companyObservableList.clear();

        // Add the updated data to the ObservableList
        companyObservableList.addAll(retrievedCompanies);

        // Refresh the ListView
        companyListView.setItems(filteredCompanyList);
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
