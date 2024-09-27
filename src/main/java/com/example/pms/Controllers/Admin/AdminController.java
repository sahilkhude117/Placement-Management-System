package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observaleValue,oldVal,newVal) -> {
                switch (newVal){
                    case ANALYTICS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getAnalyticsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.ANALYTICS);
                    }

                    case STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.STUDENTS);
                    }
                    case ADD_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getAddStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.ADD_STUDENTS);
                    }
                    case EDIT_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getEditStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.EDIT_STUDENTS);
                    }
                    case VIEW_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getViewStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.VIEW_STUDENTS);
                    }

                    case PLACED_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getPlacedStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.PLACED_STUDENTS);
                    }
                    case ADD_PLACED_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getAddPlacedStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.ADD_PLACED_STUDENTS);
                    }
                    case EDIT_PLACED_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getEditPlacedStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.EDIT_PLACED_STUDENTS);
                    }
                    case VIEW_PLACED_STUDENTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getViewPlacedStudentsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.VIEW_PLACED_STUDENTS);
                    }

                    case COMPANY -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getCompanyView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.COMPANY);
                    }
                    case ADD_COMPANIES -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getAddCompanyView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.ADD_COMPANIES);
                    }
                    case EDIT_COMPANIES -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getEditCompanyView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.EDIT_COMPANIES);
                    }
                    case VIEW_COMPANIES -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getViewCompanyView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.VIEW_COMPANIES);
                    }

                    case PROFILE -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.PROFILE);
                    }
                    case REPORTS -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getReportsView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.REPORTS);
                    }
                    default -> {
                        admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                        AdminMenuController.getInstance().updateMenuSelection(AdminMenuOptions.DASHBOARD);
                    }
                }

        });
    }
}
