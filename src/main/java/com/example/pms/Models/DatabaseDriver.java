package com.example.pms.Models;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DatabaseDriver {

    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:pms.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //logins
    public void addAdmin(String username, String email, String password, String date) {
        try {
            String query = "INSERT INTO Admins (Username, Email, Password, Date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password); // Store plaintext password
            preparedStatement.setString(4, date); // Insert the date
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean usernameExists(String username) {
        boolean exists = false;
        try {
            String query = "SELECT 1 FROM Admins WHERE Username = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // Method to get admin data by username
    public ResultSet getAdminData(String username) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Admins WHERE Username = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Admin getAdminByUsername(String username) {
        Admin admin = null;

        try {
            String query = "SELECT * FROM Admins WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String usernameFromDB = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");

                // Create the Admin object with data from the DB
                admin = new Admin(usernameFromDB, password, email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

    public void updateAdmin(String username , String email, String password) {
        String query = "UPDATE Admins SET Username = ?, Email = ?, Password = ? WHERE Username = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, username);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    * Students
    * */

    public void addStudent(Students student) {
        String query = "INSERT INTO Students (Name, Department, GPA, Skills, Address, Phone, Email, Status, Class10, Class12, StudentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            // Set the values based on the Students object
            pstmt.setString(1, student.studentNameProperty().get());
            pstmt.setString(2, student.studentDeptProperty().get());
            pstmt.setDouble(3, student.studentGpaProperty().get());
            pstmt.setString(4, student.studentSkillsProperty().get());
            pstmt.setString(5, student.studentAddressProperty().get());
            pstmt.setString(6, student.studentPhoneProperty().get());
            pstmt.setString(7, student.studentEmailProperty().get());
            pstmt.setString(8, student.studentStatusProperty().get());
            pstmt.setDouble(9, student.studentClass10Property().get());
            pstmt.setDouble(10, student.studentClass12Property().get());
            pstmt.setString(11, student.studentIDProperty().get());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Students> getAllStudents() {
        String query = "SELECT * FROM Students"; // SQL query to retrieve all students
        List<Students> studentsList = new ArrayList<>(); // List to store the retrieved students

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Create a new Students object for each row in the result set
                Students student = new Students(
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getDouble("GPA"),
                        rs.getString("Skills"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Status"),
                        rs.getDouble("Class10"),
                        rs.getDouble("Class12"),
                        rs.getString("StudentId")
                );

                // Add the student object to the list
                studentsList.addFirst(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentsList; // Return the list of students
    }

    public void updateStudent(Students student) {
        String query = "UPDATE Students SET Name = ?, Department = ?, GPA = ?, Skills = ?, Address = ?, Phone = ?, Email = ?, Status = ?, Class10 = ?, Class12 = ? WHERE StudentId = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, student.studentNameProperty().get());
            pstmt.setString(2, student.studentDeptProperty().get());
            pstmt.setDouble(3, student.studentGpaProperty().get());
            pstmt.setString(4, student.studentSkillsProperty().get());
            pstmt.setString(5, student.studentAddressProperty().get());
            pstmt.setString(6, student.studentPhoneProperty().get());
            pstmt.setString(7, student.studentEmailProperty().get());
            pstmt.setString(8, student.studentStatusProperty().get());
            pstmt.setDouble(9, student.studentClass10Property().get());
            pstmt.setDouble(10, student.studentClass12Property().get());
            pstmt.setString(11, student.studentIDProperty().get());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(String studentId) {
        String query = "DELETE FROM Students WHERE StudentId = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean studentExists(String studentId) {
        boolean exists = false;
        try {
            String query = "SELECT 1 FROM Students WHERE StudentId = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public List<String> getStudentsByStatus(String status) {
        List<String> studentIds = new ArrayList<>();
        try {
            String query = "SELECT StudentId FROM Students WHERE Status = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                studentIds.add(rs.getString("StudentId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIds;
    }

    public Students getStudentDetailsById(String studentId) {
        Students student = null;

        String query = "SELECT * FROM Students WHERE StudentId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            // Debugging output
            System.out.println("Executing query for student ID: " + studentId);

            if (rs.next()) {
                // Create a new Students object with the retrieved data
                student = new Students(
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getDouble("GPA"),
                        rs.getString("Skills"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Status"),
                        rs.getDouble("Class10"),
                        rs.getDouble("Class12"),
                        rs.getString("StudentId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public String getNextStudentId() {
        String nextStudentId = null;
        String query = "SELECT MAX(CAST(SUBSTRING(StudentId, 2) AS UNSIGNED)) AS StudentId FROM Students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int StudentId = rs.getInt("StudentId");
                nextStudentId = "S" + (StudentId + 1); // Increment and format the new ID
            } else {
                nextStudentId = "S1"; // If no students, start with S1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextStudentId;
    }









    /*
    * Placed Student
    * */


    public void addPlacedStudent(PlacedStudents placedStudent) {
        String query = "INSERT INTO PlacedStudents (Name, Department, Phone, Role, Package, Company, Email, PlacementDate, Address,StudentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {

            // Set the values based on the PlacedStudents object
            pstmt.setString(1, placedStudent.placedStudentNameProperty().get());
            pstmt.setString(2, placedStudent.placedStudentDeptProperty().get());
            pstmt.setString(3, placedStudent.placedStudentPhoneProperty().get());
            pstmt.setString(4, placedStudent.placedStudentRollProperty().get());
            pstmt.setString(5, placedStudent.placedStudentPackageProperty().get());
            pstmt.setString(6, placedStudent.placedStudentCompanyProperty().get());
            pstmt.setString(7, placedStudent.placedStudentEmailProperty().get());
            pstmt.setDate(8, java.sql.Date.valueOf(placedStudent.placedStudentDateProperty().get()));
            pstmt.setString(9, placedStudent.placedStudentAddressProperty().get());
            pstmt.setString(10,placedStudent.placedStudentIdProperty().get());

            // Execute the update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlacedStudents> getAllPlacedStudents() {
        String query = "SELECT * FROM PlacedStudents"; // SQL query to retrieve all students
        List<PlacedStudents> placedStudentsList = new ArrayList<>(); // List to store the retrieved students

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {


                PlacedStudents placedStudents = new PlacedStudents(
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getString("Phone"),
                        rs.getString("Role"),
                        rs.getString("Package"),
                        rs.getString("Company"),
                        rs.getString("Email"),
                        rs.getDate("PlacementDate").toLocalDate(),
                        rs.getString("Address"),
                        rs.getString("StudentId")
                );

                // Add the student object to the list
                placedStudentsList.addFirst(placedStudents);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return placedStudentsList; // Return the list of students
    }

    public void updatePlacedStudent(PlacedStudents placedStudents) {
        String query = "UPDATE PlacedStudents SET Name = ?, Department = ?, Phone = ?, Role = ?, Package = ?, Company = ?, Email = ?, PlacementDate = ?, Address = ? WHERE StudentId = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, placedStudents.placedStudentNameProperty().get());
            pstmt.setString(2, placedStudents.placedStudentDeptProperty().get());
            pstmt.setString(3, placedStudents.placedStudentPhoneProperty().get());
            pstmt.setString(4, placedStudents.placedStudentRollProperty().get());
            pstmt.setString(5, placedStudents.placedStudentPackageProperty().get());
            pstmt.setString(6, placedStudents.placedStudentCompanyProperty().get());
            pstmt.setString(7, placedStudents.placedStudentEmailProperty().get());
            pstmt.setDate(8, java.sql.Date.valueOf(placedStudents.placedStudentDateProperty().get()));
            pstmt.setString(9, placedStudents.placedStudentAddressProperty().get());
            pstmt.setString(10,placedStudents.placedStudentIdProperty().get());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlacedStudent(String placedStudentId) {
        String query = "DELETE FROM PlacedStudents WHERE StudentId = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, placedStudentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean placedStudentExists(String studentId) {
        boolean exists = false;
        try {
            String query = "SELECT 1 FROM PlacedStudents WHERE StudentId = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();
        String query = "SELECT Role FROM PlacedStudents";  // Fetch distinct roles

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate through the result set and add each role to the list
            while (rs.next()) {
                roles.add(rs.getString("Role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;  // Return the list of roles
    }

    public List<String> getAllPackages() {
        List<String> roles = new ArrayList<>();
        String query = "SELECT Package FROM PlacedStudents";  // Fetch distinct roles

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                roles.add(rs.getString("Package"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }



    /*
    * Company
    * */

    public void addCompany(Company company) {
        String query = "INSERT INTO Company (Name, Id, Coordinator, Phone,Email, Date, HighestPackage, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {

            // Set the values based on the Company object
            pstmt.setString(1, company.companyNameProperty().get());
            pstmt.setString(2, company.companyIdProperty().get());
            pstmt.setString(3, company.companyCoordinatorProperty().get());
            pstmt.setString(4, company.companyPhoneProperty().get());
            pstmt.setString(5, company.companyEmailProperty().get());
            pstmt.setDate(6, java.sql.Date.valueOf(company.companyDateProperty().get()));
            pstmt.setString(7, company.companyHighestPackageProperty().get());
            pstmt.setString(8, company.companyAddressProperty().get());

            // Execute the update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        String query = "SELECT * FROM Company"; // SQL query to retrieve all students
        List<Company> companyList = new ArrayList<>(); // List to store the retrieved students

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                Company company = new Company(
                        rs.getString("Name"),
                        rs.getString("Id"),
                        rs.getString("Coordinator"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getString("HighestPackage"),
                        rs.getString("Address")
                );

                // Add the student object to the list
                companyList.addFirst(company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyList;
    }

    public void updateCompany(Company company) {
        String query = "UPDATE Company SET Name = ?, Coordinator = ?, Phone = ? , Email = ?, Date = ?, HighestPackage = ?, Address = ? WHERE Id = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, company.companyNameProperty().get());
            pstmt.setString(2, company.companyCoordinatorProperty().get());
            pstmt.setString(3, company.companyPhoneProperty().get());
            pstmt.setString(4, company.companyEmailProperty().get());
            pstmt.setDate(5, java.sql.Date.valueOf(company.companyDateProperty().get()));
            pstmt.setString(6, company.companyHighestPackageProperty().get());
            pstmt.setString(7, company.companyAddressProperty().get());
            pstmt.setString(8, company.companyIdProperty().get());


            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany(String companyId) {
        String query = "DELETE FROM Company WHERE Id = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, companyId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean companyExists(String Id) {
        boolean exists = false;
        try {
            String query = "SELECT 1 FROM Company WHERE Id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public List<String> getAllCompanyNames() {
        List<String> companyNames = new ArrayList<>();
        String query = "SELECT Name FROM Company";  // Assuming your column name is 'CompanyName'

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate through the result set and add each company name to the list
            while (rs.next()) {
                companyNames.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyNames;  // Return the list of company names
    }

    public String getNextCompanyId() {
        String nextCompanyId = null;
        String query = "SELECT MAX(CAST(SUBSTRING(Id, 2) AS UNSIGNED)) AS Id FROM Company";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int Id = rs.getInt("Id");
                nextCompanyId = "C" + (Id + 1); // Increment and format the new ID
            } else {
                nextCompanyId = "C1"; // If no companies, start with C1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextCompanyId;
    }







    /*
    * Pie Chart
    * */

    public int getPlacedCount() {
        String query = "SELECT COUNT(*) FROM Students WHERE Status = ?";
        int count = 0;

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            // Set the status value for the query
            pstmt.setString(1, "Placed");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Fetch the count from result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int getHigherStudyCount() {
        String query = "SELECT COUNT(*) FROM Students WHERE Status = ?";
        int count = 0;

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            // Set the status value for the query
            pstmt.setString(1, "Opt to Higher Study");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Fetch the count from result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int getUnplacedCount() {
        String query = "SELECT COUNT(*) FROM Students WHERE Status = ?";
        int count = 0;

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            // Set the status value for the query
            pstmt.setString(1, "Unplaced");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Fetch the count from result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    /*
    * Bar Chart
    * */

    public Map<String, Integer> getPlacedCountByYear() {
        Map<String, Integer> placedCountByYear = new TreeMap<>();  // TreeMap ensures sorting by key
        String query = "SELECT strftime('%Y', datetime(PlacementDate / 1000, 'unixepoch')) AS Year, COUNT(*) AS Count " +
                "FROM PlacedStudents WHERE PlacementDate IS NOT NULL " +
                "GROUP BY strftime('%Y', datetime(PlacementDate / 1000, 'unixepoch'))";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String year = rs.getString("Year");
                int count = rs.getInt("Count");
                placedCountByYear.put(year, count);  // automatically sorted by year
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return placedCountByYear;
    }

//    public Map<String, Integer> getUnplacedCountByYear() {
//        Map<String, Integer> unplacedCountByYear = new HashMap<>();
//        String query = "SELECT strftime('%Y', datetime(PlacementDate / 1000, 'unixepoch')) AS Year, " +
//                "COUNT(*) AS Count FROM Students WHERE Status = 'Unplaced' " +
//                "GROUP BY strftime('%Y', datetime(PlacementDate / 1000, 'unixepoch'))";
//
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            while (rs.next()) {
//                String year = rs.getString("Year");
//                int count = rs.getInt("Count");
//                unplacedCountByYear.put(year, count);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return unplacedCountByYear;
//    }


    public Map<String, Integer> getCompaniesCountByYear() {
        Map<String, Integer> companiesCountByYear = new TreeMap<>();  // TreeMap ensures sorting by key
        String query = "SELECT strftime('%Y', datetime(Date / 1000, 'unixepoch')) AS Year, COUNT(*) AS Count " +
                "FROM Company WHERE Date IS NOT NULL " +
                "GROUP BY strftime('%Y', datetime(Date / 1000, 'unixepoch'))";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String year = rs.getString("Year");
                int count = rs.getInt("Count");
                companiesCountByYear.put(year, count);  // automatically sorted by year
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companiesCountByYear;
    }








}




