package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
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
                    case ANALYTICS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAnalyticsView());

                    case STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getStudentsView());
                    case ADD_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddStudentsView());
                    case EDIT_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getEditStudentsView());
                    case VIEW_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewStudentsView());

                    case PLACED_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getPlacedStudentsView());
                    case ADD_PLACED_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddPlacedStudentsView());
                    case EDIT_PLACED_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getEditPlacedStudentsView());
                    case VIEW_PLACED_STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewPlacedStudentsView());

                    case COMPANY -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCompanyView());
                    case ADD_COMPANIES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddCompanyView());
                    case EDIT_COMPANIES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getEditCompanyView());
                    case VIEW_COMPANIES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewCompanyView());

                    case PROFILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                    default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                }
        });
    }
}
