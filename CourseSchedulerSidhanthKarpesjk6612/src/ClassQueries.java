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

public class ClassQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static PreparedStatement getClass;
    private static PreparedStatement dropClass;

    private static ResultSet resultSet;    
    
    public static void addClass(ClassEntry Class)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.Class (semester, coursecode, seats) values (?,?,?)");
            addClass.setString(1, Class.getSemester());
            addClass.setString(2, Class.getCourseCode());
            addClass.setInt(3, Class.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<String> getAllCourseCodes()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> students = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select semester, courseCode, seat from app.class");
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                students.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    public static int getClassSeats(String semester, String courseCode){
       connection = DBConnection.getConnection();
       int count =0;
        try {
            getClassSeats = connection.prepareStatement("select seats from app.class where semester = (?)  and coursecode = (?)");
            //getClassSeats = connection.prepareStatement("select seats from app.classentry");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            resultSet = getClassSeats.executeQuery();
            if(resultSet.next())
                count = Integer.parseInt(resultSet.getString("seats"));
           
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return count;
    }
    public static ClassEntry getClass(String courseCode, String semester) {
        connection = DBConnection.getConnection();
        ClassEntry classEntry = null;
        try {
            getClass = connection.prepareStatement("select * from app.Class where CourseCode = ? and Semester = ? ");
            getClass.setString(1, courseCode);
            getClass.setString(2,semester);
            resultSet = getClass.executeQuery();
            
            while(resultSet.next())
            {
                classEntry= new ClassEntry(
                resultSet.getString("CourseCode"),
                resultSet.getString("Semester"),
                resultSet.getInt("Seats")
            );
            }

        }
           catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classEntry;
    
    }
    public static void dropClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try {
            dropClass = connection.prepareStatement("DELETE FROM app.class where courseCode = ? and semester = ?");
            dropClass.setString(1, courseCode);
            dropClass.setString(2,semester);
            int rowsAffected = dropClass.executeUpdate();
         
        }
           catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }    
    }
    
}
