package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.pms.Models.DatabaseDriver;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public RadioButton bug_rbtn;
    public RadioButton suggestion_rbtn;
    public RadioButton others_rbtn;
    public TextField sub_fld;
    public TextArea desc_fld;
    public Button submit_btn;
    public Button editStudentsBack_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createHoverEffect(submit_btn);
        createHoverEffect(editStudentsBack_btn);
        submit_btn.setOnAction(event -> handleSubmit());
        editStudentsBack_btn.setOnAction(event -> onBack());
    }

    private void handleSubmit() {
        String subject = sub_fld.getText();
        String description = desc_fld.getText();
        String category = ((RadioButton) (bug_rbtn.isSelected() ? bug_rbtn : suggestion_rbtn.isSelected() ? suggestion_rbtn : others_rbtn)).getText();

        if (subject.isEmpty() || description.isEmpty()) {
            showAlert("Please fill in all fields");
        } else {
            Object DatabaseDriver = null;
            boolean isSuccess = Model.getInstance().getDatabaseDriver().saveReport(category, subject, description);
            if (isSuccess) {
                showAlert("Report submitted successfully!");
                clearForm();
                Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
            } else {
                showAlert("Failed to submit the report. Please try again.");
            }
        }
    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        sub_fld.clear();
        desc_fld.clear();
        bug_rbtn.setSelected(false);
        suggestion_rbtn.setSelected(false);
        others_rbtn.setSelected(false);
    }

    private void createHoverEffect(Button btn) {
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
}
