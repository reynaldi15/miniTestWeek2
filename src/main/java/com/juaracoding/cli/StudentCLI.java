package com.juaracoding.cli;

import com.juaracoding.dao.StudentDAO;
import com.juaracoding.entity.Student;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Soft Delete Student");
            System.out.println("6. Search Students");
            System.out.println("7. View Average GPA");
            System.out.println("8. View Student Count");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        if (name.trim().isEmpty()) {
                            System.out.println("Error: Name cannot be empty!");
                            break;
                        }

                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        if (!email.contains("@")) {
                            System.out.println("Error: Invalid email format!");
                            break;
                        }

                        System.out.print("Enter age: ");
                        int age;
                        try {
                            age = scanner.nextInt();
                            if (age <= 0) {
                                System.out.println("Error: Age must be greater than 0!");
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Age must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter major: ");
                        String major = scanner.nextLine();
                        if (major.trim().isEmpty()) {
                            System.out.println("Error: Major cannot be empty!");
                            break;
                        }

                        System.out.print("Enter GPA: ");
                        double gpa;
                        try {
                            gpa = scanner.nextDouble();
                            if (gpa < 0.0 || gpa > 4.0) {
                                System.out.println("Error: GPA must be between 0.0 and 4.0!");
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: GPA must be a valid decimal number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        Student student = new Student(0, name, email, age, major, gpa);
                        studentDAO.addStudent(student);
                        System.out.println("Student added successfully!");
                        break;

                    case 2:
                        List<Student> students = studentDAO.getAllStudents();
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            for (Student s : students) {
                                System.out.println(s);
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter student ID to update: ");
                        int id;
                        try {
                            id = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: ID must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        email = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        try {
                            age = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Age must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new major: ");
                        major = scanner.nextLine();
                        System.out.print("Enter new GPA: ");
                        try {
                            gpa = scanner.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: GPA must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        student = new Student(id, name, email, age, major, gpa);
                        studentDAO.updateStudent(student);
                        System.out.println("Student updated successfully!");
                        break;

                    case 4:
                        System.out.print("Enter student ID to delete: ");
                        try {
                            id = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: ID must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        studentDAO.deleteStudent(id);
                        System.out.println("Student deleted successfully!");
                        break;

                    case 5:
                        System.out.print("Enter student ID to do soft delete: ");
                        try {
                            id = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: ID must be a number!");
                            scanner.nextLine();
                            break;
                        }

                        scanner.nextLine(); // Consume newline
                        studentDAO.softDeleteStudent(id);
                        System.out.println("Student soft deleted successfully!");
                        break;

                    case 6:
                        System.out.print("Enter search keyword: ");
                        String keyword = scanner.nextLine();
                        students = studentDAO.searchStudents(keyword);
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            for (Student s : students) {
                                System.out.println(s);
                            }
                        }
                        break;

                    case 7:
                        double avgGpa = studentDAO.getAverageGpa();
                        System.out.println("Average GPA: " + avgGpa);
                        break;

                    case 8:
                        int count = studentDAO.getStudentCount();
                        System.out.println("Total Students: " + count);
                        break;

                    case 9:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option! Please choose a number between 1-9.");
                }
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }
    }
}
