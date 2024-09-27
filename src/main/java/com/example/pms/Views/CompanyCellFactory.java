package com.example.pms.Views;

import com.example.pms.Controllers.Admin.CompanyCellController;
import com.example.pms.Models.Company;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CompanyCellFactory extends ListCell<Company> {

    @Override
    protected void updateItem(Company company, boolean empty) {
        super.updateItem(company, empty);


        if (empty || company == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Load the FXML file for the student cell
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/CompanyCell.fxml"));

                // Create the controller and set the student data
                CompanyCellController controller = new CompanyCellController(company);
                loader.setController(controller);

                // Load the FXML and set it as the graphic
                AnchorPane companyCell = loader.load();
                setGraphic(companyCell);



                this.setStyle("-fx-background-color: transparent;");

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors gracefully by resetting the cell
                setText("Error loading cell");
                setGraphic(null);
            }
        }
    }
}
