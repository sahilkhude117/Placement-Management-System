package com.example.pms.Controllers;

import com.example.pms.Models.Admin;
import com.example.pms.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameL_fld;
    public PasswordField passL_fld;
    public Button login_btn;
    public Label signupL_lbl;

    private Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> onLogin());
        signupL_lbl.setOnMouseClicked(event -> showSignUpWindow());
    }

    private void onLogin() {
        Stage stage = (Stage) signupL_lbl.getScene().getWindow();

        // Get user input
        String username = usernameL_fld.getText();
        String password = passL_fld.getText();

        // Check for empty input
        if (username.isEmpty() || password.isEmpty()) {
            // Display an error message to the user
            showAlert("Username and password cannot be empty.");
            return;
        }


        // Evaluate Login Credentials
        Model.getInstance().evaluateAdminCred(username, password);
        if (Model.getInstance().getAdminLoginSuccessFlag()) {
            Admin admin = Model.getInstance().getDatabaseDriver().getAdminByUsername(username);

            if (admin != null) {
                // Store the logged-in admin globally in the Model
                Model.getInstance().setLoggedAdmin(admin);
                Model.getInstance().getViewFactory().showAdminWindow();
                // Close login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                showAlert("Failed to retrieve admin details.");
            }

        } else {
            showAlert("Invalid username or password.");
        }
    }

    private void showSignUpWindow() {
        Stage stage = (Stage) signupL_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showSignUpWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

