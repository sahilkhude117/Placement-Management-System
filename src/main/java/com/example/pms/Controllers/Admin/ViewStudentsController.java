package com.example.pms.Controllers.Admin;

import com.example.pms.Models.Model;
import com.example.pms.Models.Students;
import com.example.pms.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewStudentsController implements Initializable {
    public Label viewStudentsName1_lbl;
    public Label viewStudentName2_lbl;
    public Label viewStudentDept_lbl;
    public Label viewStudentsSkills_lbl;
    public Label viewStudentGpa_lbl;
    public Label viewStudentsAdr_lbl;
    public Label viewStudentsPhone_lbl;
    public Label viewStudentsEmail_lbl;
    public Label viewStudentsStatus_lbl;
    public Label viewStudents10_lbl;
    public Label viewStudents12_lbl;
    public Button viewStudentsBack_btn;
    public Label viewStudentID_lbl1;

    private Students student;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            viewStudentsBack_btn.setOnAction(event -> onBack());
    }

    public void setStudentData(Students student) {
        this.student = student;

        // Populate fields with student data
        viewStudentsName1_lbl.setText(student.studentNameProperty().getValue());
        viewStudentName2_lbl.setText(student.studentNameProperty().getValue());
        viewStudentDept_lbl.setText(student.studentDeptProperty().getValue());
        viewStudentsSkills_lbl.setText(student.studentSkillsProperty().getValue());
        viewStudentGpa_lbl.setText(String.valueOf(student.studentGpaProperty().getValue()));
        viewStudentsAdr_lbl.setText(student.studentAddressProperty().getValue());
        viewStudentsPhone_lbl.setText(student.studentPhoneProperty().getValue());
        viewStudentsEmail_lbl.setText(student.studentEmailProperty().getValue());
        viewStudentsStatus_lbl.setText(student.studentStatusProperty().getValue());
        viewStudents10_lbl.setText(String.valueOf(student.studentClass10Property().getValue()));
        viewStudents12_lbl.setText(String.valueOf(student.studentClass12Property().getValue()));
        viewStudentID_lbl1.setText(student.studentIDProperty().getValue());
    }

    private void onBack(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STUDENTS);
    }


}
