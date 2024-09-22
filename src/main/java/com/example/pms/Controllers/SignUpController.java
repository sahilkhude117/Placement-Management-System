package com.example.pms.Controllers;

import com.example.pms.Controllers.Admin.ProfileController;
import com.example.pms.Models.Admin;
import com.example.pms.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public TextField usernameS_fld;
    public TextField emailS_fld;
    public PasswordField passS_fld;
    public PasswordField confirmPassS_fld; // New field for password confirmation
    public Button signup_btn;
    public Label loginL_lbl;
    public CheckBox termsS_cb;

    private Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signup_btn.setOnAction(event -> onSignUp());
        loginL_lbl.setOnMouseClicked(event -> showLoginWindow());
    }

    private void onSignUp() {
        Stage stage = (Stage) loginL_lbl.getScene().getWindow();

        String username = usernameS_fld.getText();
        String email = emailS_fld.getText();
        String password = passS_fld.getText();
        String confirmPassword = confirmPassS_fld.getText(); // Get confirmation password

        // Check if any field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        // Check if the username already exists
        boolean usernameExists = Model.getInstance().getDatabaseDriver().usernameExists(username);
        if (usernameExists) {
            showAlert("Error", "Username already exists.");
            return;
        }

        // Check password strength
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d).{8,}$";
        if (!password.matches(passwordPattern)) {
            showAlert("Error", "Password must be at least 8 characters long, include one uppercase letter, one number, and one special symbol.");
            return;
        }

        // Confirm password match
        if (!password.equals(confirmPassword)) { // Check if passwords match
            showAlert("Error", "Passwords do not match.");
            return;
        }

        // Check if terms and conditions are accepted
        if (!termsS_cb.isSelected()) {
            showAlert("Error", "You must accept the terms and conditions.");
            return;
        }

        // Add new user to the database
        LocalDate currentDate = LocalDate.now();
        Model.getInstance().getDatabaseDriver().addAdmin(username, email, password, currentDate.toString());

        // Get the newly created admin from the database
        Admin admin = Model.getInstance().getDatabaseDriver().getAdminByUsername(username);

        if (admin == null) {
            showAlert("Error", "Failed to create a new user. Please try again.");
            return;
        }

        // Set the newly registered admin as the logged-in admin
        Model.getInstance().setLoggedAdmin(admin);

        // Update the profile UI immediately after signup
        Model.getInstance().getViewFactory().showAdminWindow();

        // Close the signup window
        Model.getInstance().getViewFactory().closeStage(stage);
    }
    

    private void showLoginWindow() {
        Stage stage = (Stage) loginL_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

