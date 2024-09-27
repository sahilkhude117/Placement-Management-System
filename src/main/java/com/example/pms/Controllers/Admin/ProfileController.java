package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Admin;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Label username_lbl;
    public Button logout_btn;
    public Label username1_lbl;
    public TextField update_Email_fld;
    public TextField update_OldPass_fld;
    public TextField update_NewPass_fld;
    public Button update_btn;

    private Admin admin;  // Admin object to store logged-in admin data

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        admin = Model.getInstance().getLoggedAdmin();

        if (admin != null) {
            setAdminData(admin);
        }

        createHoverEffect(logout_btn);
        createHoverEffect(update_btn);

        // Handle logout button action
        logout_btn.setOnAction(event -> onLogout());

        // Handle update button action
        update_btn.setOnAction(event -> onUpdate());
    }

    // This method binds the Admin model's data to the UI fields
    public void setAdminData(Admin admin) {
        this.admin = admin;

        // Binding the admin properties to the text fields and label
        username_lbl.textProperty().bind(admin.usernameProperty());
        username1_lbl.textProperty().bindBidirectional(admin.usernameProperty());
        update_Email_fld.textProperty().bindBidirectional(admin.emailProperty());
    }

    private void onLogout() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Logout Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to Log Out?");
        confirmationAlert.setContentText("");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) username_lbl.getScene().getWindow();
                Model.getInstance().getViewFactory().showLoginWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            }
        });
    }

    private void onUpdate() {
        String username = username1_lbl.getText();
        String email = update_Email_fld.getText();
        String oldPassword = update_OldPass_fld.getText();
        String newPassword = update_NewPass_fld.getText();

        // Validate form fields
        if (username.isEmpty() || email.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        // Check if the username already exists in the database (excluding current username)
        boolean usernameExists = Model.getInstance().getDatabaseDriver().usernameExists(username);
        if (usernameExists && !username.equals(admin.usernameProperty().get())) {
            showAlert("Error", "Username already exists.");
            return;
        }

        // Check password strength
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d).{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            showAlert("Error", "Password must be at least 8 characters long, include one uppercase letter, one number, and one special symbol.");
            return;
        }

        // Check if old password matches the current admin password
        if (!admin.passwordProperty().get().equals(oldPassword)) {
            showAlert("Error", "Old password is incorrect.");
            return;
        }

        // If the validation passes, update the admin password in the database and the model
        admin.passwordProperty().set(newPassword); // Update the password in the model
        Model.getInstance().getDatabaseDriver().updateAdmin(username, email, newPassword); // Update in DB

        showAlert("Success", "User Updated Successfully!");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
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


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
