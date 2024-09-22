package com.example.pms.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Students {

    private final StringProperty studentName;
    private final StringProperty studentDept;
    private final DoubleProperty studentGpa;
    private final StringProperty studentSkills;
    private final StringProperty studentAddress;
    private final StringProperty studentPhone;
    private final StringProperty studentEmail;
    private final StringProperty studentStatus;
    private final DoubleProperty studentClass10;
    private final DoubleProperty studentClass12;
    private final StringProperty studentId;
    private final ObjectProperty<LocalDate> studentDate;

    public Students(String studentName, String studentDept, Double studentGpa, String studentSkills, String studentAddress, String studentPhone, String studentEmail, String studentStatus, Double studentClass10, Double studentClass12,String studentId ) {
        this.studentName = new SimpleStringProperty(this, "StudentName", studentName);
        this.studentDept = new SimpleStringProperty(this, "StudentDept", studentDept);
        this.studentGpa = new SimpleDoubleProperty(this, "StudentGpa", studentGpa);
        this.studentSkills = new SimpleStringProperty(this, "StudentSkills", studentSkills);
        this.studentAddress = new SimpleStringProperty(this, "StudentAddress", studentAddress);
        this.studentPhone = new SimpleStringProperty(this, "StudentPhone", studentPhone);
        this.studentEmail = new SimpleStringProperty(this, "StudentEmail", studentEmail);
        this.studentStatus = new SimpleStringProperty(this, "StudentStatus", studentStatus);
        this.studentClass10 = new SimpleDoubleProperty(this, "StudentClass10", studentClass10);
        this.studentClass12 = new SimpleDoubleProperty(this, "StudentClass12", studentClass12);
        this.studentId = new SimpleStringProperty(this, "StudentID", studentId);
        this.studentDate = new SimpleObjectProperty<>(this, "StudentDate", LocalDate.now());
    }

    // Getters for properties
    public StringProperty studentNameProperty() { return this.studentName; }
    public StringProperty studentDeptProperty() { return this.studentDept; }
    public DoubleProperty studentGpaProperty() { return this.studentGpa; }
    public StringProperty studentSkillsProperty() { return this.studentSkills; }
    public StringProperty studentAddressProperty() { return this.studentAddress; }
    public StringProperty studentPhoneProperty() { return this.studentPhone; }
    public StringProperty studentEmailProperty() { return this.studentEmail; }
    public StringProperty studentStatusProperty() { return this.studentStatus; }
    public DoubleProperty studentClass10Property() { return this.studentClass10; }
    public DoubleProperty studentClass12Property() { return this.studentClass12; }
    public StringProperty studentIDProperty() { return this.studentId; }
    public ObjectProperty<LocalDate> studentDateProperty() { return this.studentDate; }
}
