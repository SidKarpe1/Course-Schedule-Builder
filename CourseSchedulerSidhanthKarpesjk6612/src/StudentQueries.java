/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sid
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static ResultSet resultSet;    
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentid, firstname, lastname from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry studentEntry = null;
        try {
            getStudent = connection.prepareStatement("select * from app.Student where studentID = ? ");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
            {
                studentEntry= new StudentEntry(
                resultSet.getString("studentID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName")
            );
            }

        }
           catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        

        return studentEntry;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("DELETE FROM app.Student where studentid = ?");
            dropStudent.setString(1, studentID);
            int rowsAffected = dropStudent.executeUpdate();
         
        }
           catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }    
    }
    
           
}
