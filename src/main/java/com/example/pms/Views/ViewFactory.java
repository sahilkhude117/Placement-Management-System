package com.example.pms.Views;

import com.example.pms.Controllers.Admin.*;
import com.example.pms.Models.Admin;
import com.example.pms.Models.Company;
import com.example.pms.Models.PlacedStudents;
import com.example.pms.Models.Students;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewFactory {

    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private final Map<AdminMenuOptions, Object> controllers;

    private AnchorPane dashboardView;
    private AnchorPane analyticsView;

    // Students Section
    private AnchorPane studentsView;
    private AnchorPane addStudentsView;
    private AnchorPane editStudentsView;
    private AnchorPane viewStudentsView;

    //Placed Student Section
    private AnchorPane placedStudentsView;
    private AnchorPane addPlacedStudentsView;
    private AnchorPane editPlacedStudentsView;
    private AnchorPane viewPlacedStudentsView;

    //Company Section
    private AnchorPane companyView;
    private AnchorPane addCompanyView;
    private AnchorPane editCompanyView;
    private AnchorPane viewCompanyView;

    private AnchorPane profileView;
    private AnchorPane reportsView;


    public ViewFactory() {
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        this.controllers = new HashMap<>();
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }


    /*
    * Menu Options
    * */

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getAnalyticsView() {
        if (analyticsView == null) {
            try {
                analyticsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Analytics.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return analyticsView;
    }

    /*
    * Admin
    * */


    public void updateAdminData(Admin admin) {
        ProfileController controller = (ProfileController) controllers.get(AdminMenuOptions.PROFILE);
        if (controller != null) {
            // Pass the student data to the controller
            controller.setAdminData(admin);
        } else {
            System.out.println("ProfileController is not loaded yet.");
        }
    }


    /*
    * Students Section
    * */

    public AnchorPane getStudentsView() {
        if (studentsView == null) {
            try {
                studentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Students.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentsView;
    }

    public AnchorPane getAddStudentsView() {
        if (addStudentsView == null) {
            try {
                addStudentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddStudents.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addStudentsView;
    }

    public AnchorPane getEditStudentsView() {
        if (editStudentsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditStudents.fxml"));
                editStudentsView = loader.load();
                controllers.put(AdminMenuOptions.EDIT_STUDENTS, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editStudentsView;
    }

    public AnchorPane getViewStudentsView() {
        if (viewStudentsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ViewStudents.fxml"));
                viewStudentsView = loader.load();
                controllers.put(AdminMenuOptions.VIEW_STUDENTS, loader.getController());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return viewStudentsView;
    }

    public void updateViewStudentsData(Students student) {
        // Retrieve the ViewStudentsController from the map
        ViewStudentsController controller = (ViewStudentsController) controllers.get(AdminMenuOptions.VIEW_STUDENTS);
        if (controller != null) {
            // Pass the student data to the controller
            controller.setStudentData(student);
        } else {
            System.out.println("ViewStudentsController is not loaded yet.");
        }
    }


    /*
    * Placed Students Section
    * */

    public AnchorPane getPlacedStudentsView() {
        if (placedStudentsView == null) {
            try {
                placedStudentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/PlacedStudents.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return placedStudentsView;
    }

    public AnchorPane getAddPlacedStudentsView() {
        if (addPlacedStudentsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddPlacedStudents.fxml"));
                addPlacedStudentsView = loader.load();
                controllers.put(AdminMenuOptions.ADD_PLACED_STUDENTS, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addPlacedStudentsView;
    }


    public AnchorPane getEditPlacedStudentsView(){
        if (editPlacedStudentsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditPlacedStudents.fxml"));
                editPlacedStudentsView = loader.load();
                controllers.put(AdminMenuOptions.EDIT_PLACED_STUDENTS, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editPlacedStudentsView;
    }

    public AnchorPane getViewPlacedStudentsView() {
        if (viewPlacedStudentsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ViewPlacedStudents.fxml"));
                viewPlacedStudentsView = loader.load();
                controllers.put(AdminMenuOptions.VIEW_PLACED_STUDENTS, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viewPlacedStudentsView;
    }

    public void updateViewPlacedStudentsData(PlacedStudents placedStudents) {
        // Retrieve the ViewStudentsController from the map
        ViewPlacedStudentsController controller = (ViewPlacedStudentsController) controllers.get(AdminMenuOptions.VIEW_PLACED_STUDENTS);
        if (controller != null) {
            // Pass the student data to the controller
            controller.setPlacedStudentsData(placedStudents);
        } else {
            System.out.println("ViewPlacedStudentsController is not loaded yet.");
        }
    }


    /*
    * Company Views Section
    * */

    public AnchorPane getCompanyView() {
        if (companyView == null) {
            try {
                companyView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Company.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return companyView;
    }

    public AnchorPane getAddCompanyView() {
        if (addCompanyView == null) {
            try {
                addCompanyView = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddCompany.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addCompanyView;
    }

    public AnchorPane getEditCompanyView() {
        if (editCompanyView == null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditCompany.fxml"));
                editCompanyView = loader.load();
                controllers.put(AdminMenuOptions.EDIT_COMPANIES, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editCompanyView;
    }

    public AnchorPane getViewCompanyView() {
        if (viewCompanyView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ViewCompany.fxml"));
                viewCompanyView = loader.load();
                controllers.put(AdminMenuOptions.VIEW_COMPANIES, loader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viewCompanyView;
    }

    public void updateViewCompanyData(Company company) {
        // Retrieve the ViewStudentsController from the map
        ViewCompanyController controller = (ViewCompanyController) controllers.get(AdminMenuOptions.VIEW_COMPANIES);
        if (controller != null) {
            // Pass the student data to the controller
            controller.setCompanyData(company);
        } else {
            System.out.println("ViewPlacedStudentsController is not loaded yet.");
        }
    }



    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }

    public AnchorPane getReportsView(){
        if (reportsView == null) {
            try {
                reportsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Reports.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reportsView;
    }



    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showSignUpWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SignUp.fxml"));
        createStage(loader);
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/logo1.jpg"))));
        stage.setResizable(false);
        stage.setTitle("Placement Management System");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }


    // Add method to get the controller
    public Object getController(AdminMenuOptions menuOption) {
        return controllers.get(menuOption);
    }

}
