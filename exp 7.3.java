import java.sql.*;
import java.util.Scanner;

class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    // Getters and Setters
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
}

class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your database name
    private static final String USER = "your_username"; // Replace with your database username
    private static final String PASSWORD = "your_password"; // Replace with your database password

    public void createStudent(Student student) {
        String query = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getDepartment());
            preparedStatement.setDouble(4, student.getMarks());
            preparedStatement.executeUpdate();
            System.out.println("Student created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readStudents() {
        String query = "SELECT * FROM Student";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("StudentID: " + resultSet.getInt("StudentID") +
                        ", Name: " + resultSet.getString("Name") +
                        ", Department: " + resultSet.getString("Department") +
                        ", Marks: " + resultSet.getDouble("Marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String query = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, student .getName());
            preparedStatement.setString(2, student.getDepartment());
            preparedStatement.setDouble(3, student.getMarks());
            preparedStatement.setInt(4, student.getStudentID());
            preparedStatement.executeUpdate();
            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentID) {
        String query = "DELETE FROM Student WHERE StudentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentController controller = new StudentController();
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Create Student");
            System.out.println("2. Read Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Student newStudent = new Student();
                    System.out.print("Enter StudentID: ");
                    newStudent.setStudentID(scanner.nextInt());
                    System.out.print("Enter Name: ");
                    newStudent.setName(scanner.next());
                    System.out.print("Enter Department: ");
                    newStudent.setDepartment(scanner.next());
                    System.out.print("Enter Marks: ");
                    newStudent.setMarks(scanner.nextDouble());
                    controller.createStudent(newStudent);
                    break;
                case 2:
                    controller.readStudents();
                    break;
                case 3:
                    Student updatedStudent = new Student();
                    System.out.print("Enter StudentID to update: ");
                    updatedStudent.setStudentID(scanner.nextInt());
                    System.out.print("Enter new Name: ");
                    updatedStudent.setName(scanner.next());
                    System.out.print("Enter new Department: ");
                    updatedStudent.setDepartment(scanner.next());
                    System.out.print("Enter new Marks: ");
                    updatedStudent.setMarks(scanner.nextDouble());
                    controller.updateStudent(updatedStudent);
                    break;
                case 4:
                    System.out.print("Enter StudentID to delete: ");
                    int studentID = scanner.nextInt();
                    controller.deleteStudent(studentID);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
}
