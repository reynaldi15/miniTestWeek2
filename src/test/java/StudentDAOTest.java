import com.juaracoding.dao.StudentDAO;
import com.juaracoding.entity.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOTest {
    private StudentDAO studentDAO;

    @BeforeClass
    public void setup() {
        studentDAO = new StudentDAO();
    }

    @Test
    public void testAddStudent() throws SQLException {
        Student student = new Student(0, "John Doe", "john@example.com", 20, "Computer Science", 3.5);
        studentDAO.addStudent(student);
        List<Student> students = studentDAO.getAllStudents();
        Assert.assertTrue(students.stream().anyMatch(s -> s.getEmail().equals("john@example.com")));
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testUpdateStudent() throws SQLException {
        Student student = new Student(1, "John Doe Updated", "john@example.com", 21, "Computer Science", 3.7);
        studentDAO.updateStudent(student);
        List<Student> students = studentDAO.getAllStudents();
        Assert.assertTrue(students.stream().anyMatch(s -> s.getName().equals("John Doe Updated")));
    }

    @Test(dependsOnMethods = "testUpdateStudent")
    public void testDeleteStudent() throws SQLException {
        studentDAO.deleteStudent(1);
        List<Student> students = studentDAO.getAllStudents();
        Assert.assertFalse(students.stream().anyMatch(s -> s.getId() == 1));
    }

    @Test
    public void testSearchStudents() throws SQLException {
        List<Student> students = studentDAO.searchStudents("Computer");
        Assert.assertTrue(students.size() > 0);
    }

    @Test
    public void testGetAverageGpa() throws SQLException {
        double avgGpa = studentDAO.getAverageGpa();
        Assert.assertTrue(avgGpa > 0);
    }

    @Test
    public void testGetStudentCount() throws SQLException {
        int count = studentDAO.getStudentCount();
        Assert.assertTrue(count > 0);
    }
}
