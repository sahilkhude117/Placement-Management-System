package com.example.pms.Controllers.Admin;
import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

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
            handleButtonSelection(dashboard_btn);
            onDashboard();
        });
        analytics_btn.setOnAction(event -> {
            handleButtonSelection(analytics_btn);
            onAnalytics();
        });
        placedStud_btn.setOnAction(event -> {
            handleButtonSelection(placedStud_btn);
            onPlacedStudents();
        });
        stud_btn.setOnAction(event -> {
            handleButtonSelection(stud_btn);
            onStudents();
        });
        company_btn.setOnAction(event -> {
            handleButtonSelection(company_btn);
            onCompany();
        });
        profile_btn.setOnAction(event -> {
            handleButtonSelection(profile_btn);
            onProfile();
        });
        report_btn.setOnAction(event -> {
            handleButtonSelection(report_btn);
            onReport();
        });
    }

    // Method to change button styles
    private void handleButtonSelection(Button clickedButton) {
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
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
    }

    private void onAnalytics() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ANALYTICS);
    }

    private void onStudents() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
    }

    private void onPlacedStudents() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PLACED_STUDENTS);
    }

    private void onCompany() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPANY);
    }

    private void onProfile() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PROFILE);
    }

    private void onReport() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_COMPANIES);
    }


    // Method to update menu selection programmatically
    public void updateMenuSelection(AdminMenuOptions selectedOption) {
        // Reset all button styles to the default
        handleButtonSelection(null); // Reset the previous selection

        // Apply selected styles based on the selected option
        switch (selectedOption) {
            case DASHBOARD:
                handleButtonSelection(dashboard_btn);
                break;
            case ANALYTICS:
                handleButtonSelection(analytics_btn);
                break;
            case STUDENTS:
                handleButtonSelection(stud_btn);
                break;
            case PLACED_STUDENTS:
                handleButtonSelection(placedStud_btn);
                break;
            case COMPANY:
                handleButtonSelection(company_btn);
                break;
            case PROFILE:
                handleButtonSelection(profile_btn);
                break;
            case ADD_COMPANIES:
                handleButtonSelection(report_btn);
                break;
            // Add cases for other menu items if needed
        }
    }

}
