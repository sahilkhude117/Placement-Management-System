package com.example.pms.Controllers.Admin;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;


public class AdminMenuController implements Initializable {
    public Button dashboard_btn;
    public Button analytics_btn;
    public Button placedStud_btn;
    public Button stud_btn;
    public Button company_btn;
    public Button profile_btn;
    public Button report_btn;

    // Track the currently selected button
    private Button selectedButton = null;
    private static AdminMenuController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set default style for all buttons
        dashboard_btn.getStyleClass().add("admin-menu-button");
        analytics_btn.getStyleClass().add("admin-menu-button");
        placedStud_btn.getStyleClass().add("admin-menu-button");
        stud_btn.getStyleClass().add("admin-menu-button");
        company_btn.getStyleClass().add("admin-menu-button");
        profile_btn.getStyleClass().add("admin-menu-button");
        report_btn.getStyleClass().add("alertButton");

        // Add listeners to buttons
        addListeners();

        createButtonHoverEffect(dashboard_btn);
        createButtonHoverEffect(analytics_btn);
        createButtonHoverEffect(placedStud_btn);
        createButtonHoverEffect(stud_btn);
        createButtonHoverEffect(company_btn);
        createButtonHoverEffect(profile_btn);
        createButtonHoverEffect(report_btn);

        // Set the default selected button (optional)
        handleButtonSelection(dashboard_btn); // Default to dashboard on startup
    }


    public AdminMenuController() {
        // Constructor logic
        instance = this;  // Set the singleton instance when the controller is created
    }

    // Provide a way to access the singleton instance
    public static AdminMenuController getInstance() {
        return instance;
    }


    private void addListeners() {
        dashboard_btn.setOnAction(event -> {
            onDashboard();
        });
        analytics_btn.setOnAction(event -> {
            onAnalytics();
        });
        placedStud_btn.setOnAction(event -> {

            onPlacedStudents();
        });
        stud_btn.setOnAction(event -> {

            onStudents();
        });
        company_btn.setOnAction(event -> {

            onCompany();
        });
        profile_btn.setOnAction(event -> {

            onProfile();
        });
        report_btn.setOnAction(event -> {

            onReport();
        });
    }

    // Method to change button styles
    public void handleButtonSelection(Button clickedButton) {
        if (selectedButton != null) {
            // Remove the selected style from the previously selected button
            selectedButton.getStyleClass().remove("admin-menu-button-selected");
            selectedButton.getStyleClass().add("admin-menu-button"); // Reapply default style
        }

        // Apply the selected style to the clicked button
        clickedButton.getStyleClass().remove("admin-menu-button");
        clickedButton.getStyleClass().add("admin-menu-button-selected");

        // Update the currently selected button
        selectedButton = clickedButton;
    }

    private void onDashboard() {
        handleButtonSelection(dashboard_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
    }

    private void onAnalytics() {
        handleButtonSelection(analytics_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ANALYTICS);
    }

    private void onStudents() {
        handleButtonSelection(stud_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
    }

    private void onPlacedStudents() {
        handleButtonSelection(placedStud_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
    }

    private void onCompany() {
        handleButtonSelection(company_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
    }

    private void onProfile() {
        handleButtonSelection(profile_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PROFILE);
    }

    private void onReport() {
        handleButtonSelection(report_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.REPORTS);
    }

    private void createButtonHoverEffect(Button button) {
        // Create scale transition for the button
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), button);
        scaleIn.setToX(1.1);  // Scale to 110% on hover
        scaleIn.setToY(1.1);  // Scale to 110% on hover

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), button);
        scaleOut.setToX(1);   // Scale back to normal when not hovered
        scaleOut.setToY(1);   // Scale back to normal when not hovered

        // Set hover event listeners
        button.setOnMouseEntered(e -> scaleIn.playFromStart());
        button.setOnMouseExited(e -> scaleOut.playFromStart());
    }

    public void updateMenuSelection(AdminMenuOptions selectedOption) {
        // Reset all button styles to the default
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("admin-menu-button-selected");
            selectedButton.getStyleClass().add("admin-menu-button"); // Reapply default style
        }

        // Apply selected styles based on the selected option
        switch (selectedOption) {
            case DASHBOARD:
                handleButtonSelection(dashboard_btn);
                break;
            case ANALYTICS:
                handleButtonSelection(analytics_btn);
                break;
            case STUDENTS, ADD_STUDENTS, VIEW_STUDENTS, EDIT_STUDENTS:
                handleButtonSelection(stud_btn);
                break;
            case PLACED_STUDENTS, ADD_PLACED_STUDENTS, VIEW_PLACED_STUDENTS, EDIT_PLACED_STUDENTS:
                handleButtonSelection(placedStud_btn);
                break;
            case COMPANY ,ADD_COMPANIES, VIEW_COMPANIES, EDIT_COMPANIES:
                handleButtonSelection(company_btn);
                break;
            case PROFILE:
                handleButtonSelection(profile_btn);
                break;
            case REPORTS:
                handleButtonSelection(report_btn);
                break;
        }
    }


}
