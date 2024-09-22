package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPlacedStudentsController implements Initializable {
    public Label viewPSName1_lbl;
    public Label viewPSName2_lbl;
    public Label viewPSDept_lbl;
    public Label viewPSCompany_lbl;
    public Label viewPSRoll_lbl;
    public Label viewPSPackage_lbl;
    public Label viewPSDate_lbl;
    public Label viewPSPhone_lbl;
    public Label viewPSAdr_lbl;
    public Label viewPSEmail_lbl;
    public Button viewPSBack_btn;

    private PlacedStudents placedStudents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewPSBack_btn.setOnAction(e -> onBack());
    }

    public void setPlacedStudentsData (PlacedStudents placedStudents) {
        this.placedStudents = placedStudents;

        viewPSName2_lbl.setText(placedStudents.placedStudentNameProperty().getValue());
        viewPSName1_lbl.setText(placedStudents.placedStudentNameProperty().getValue());
        viewPSDept_lbl.setText(placedStudents.placedStudentDeptProperty().getValue());
        viewPSCompany_lbl.setText(placedStudents.placedStudentCompanyProperty().getValue());
        viewPSRoll_lbl.setText(placedStudents.placedStudentRollProperty().getValue());
        viewPSPackage_lbl.setText(placedStudents.placedStudentPackageProperty().getValue());
        viewPSDate_lbl.setText(placedStudents.placedStudentDateProperty().getValue() != null ? placedStudents.placedStudentDateProperty().getValue().toString() : "Date not available");
        viewPSPhone_lbl.setText(placedStudents.placedStudentPhoneProperty().getValue());
        viewPSAdr_lbl.setText(placedStudents.placedStudentAddressProperty().getValue());
        viewPSEmail_lbl.setText(placedStudents.placedStudentEmailProperty().getValue());

    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
    }
}
