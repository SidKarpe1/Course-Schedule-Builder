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

public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
//    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseDescriptions;
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentByClass;
    private static PreparedStatement getWaitlistedStudentByClass;
    
   public static ArrayList<CourseDescription> getAllCouseDescriptions(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseDescription> descriptions = new ArrayList<CourseDescription>();
        try {
            getAllCourseDescriptions = connection.prepareStatement("select app.class.courseCode, coursedescription, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllCourseDescriptions.setString(1, semester);
            resultSet = getAllCourseDescriptions.executeQuery();
            
            while (resultSet.next()) {
                descriptions.add(new CourseDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return descriptions;
    
    }
   public static ArrayList<StudentEntry> getScheduledStudentByClass(String semester, String courseCode){
       connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try {
            getScheduledStudentByClass = connection.prepareStatement("select * from app.student where studentID in (select StudentID from app.schedule where courseCode = ? and semester = ? and status = ?)");
            getScheduledStudentByClass.setString(1, courseCode);
            getScheduledStudentByClass.setString(2, semester);
            getScheduledStudentByClass.setString(3, "Scheduled");
            resultSet = getScheduledStudentByClass.executeQuery();
            
            while (resultSet.next()) {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return students;
    }
   public static ArrayList<StudentEntry> getWaitlistedStudentByClass(String semester, String courseCode){
       connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try {
            getWaitlistedStudentByClass = connection.prepareStatement("select * from app.student where studentID in (select StudentID from app.schedule where courseCode = ? and semester = ? and status = ?)");
            getWaitlistedStudentByClass.setString(1, courseCode);
            getWaitlistedStudentByClass.setString(2, semester);
            getWaitlistedStudentByClass.setString(3, "Wait Listed");
            resultSet = getWaitlistedStudentByClass.executeQuery();
            
            while (resultSet.next()) {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return students;
    }
}
