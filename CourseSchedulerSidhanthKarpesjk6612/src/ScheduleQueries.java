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

public class ScheduleQueries {
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentsByClass;
    private static Connection connection;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement addStudentEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement dropScheduleByCourse;

    public static ArrayList<ScheduleEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> retList = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select * from app.schedule where semester = (?) and couseCode = (?) and status = (?)");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            getScheduledStudentsByClass.setString(3, "Scheduled");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                retList.add(new ScheduleEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3),resultSet.getString(4), resultSet.getTimestamp(5)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return retList;
    }
        public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;
        try {
            getScheduledStudentCount = connection.prepareStatement("select count(studentid) from app.schedule where semester = (?) and coursecode = (?)");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            
            resultSet = getScheduledStudentCount.executeQuery();
            while (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString(1));
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return count;
    }
        public static void  addStudentEntry(ScheduleEntry entry){
            connection = DBConnection.getConnection();
        try {
            addStudentEntry = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addStudentEntry.setString(1, entry.getSemester());
            addStudentEntry.setString(2, entry.getCourseCode());
            addStudentEntry.setString(3, entry.getStudentID());
            addStudentEntry.setString(4, entry.getStatus());
            addStudentEntry.setTimestamp(5, entry.getTimestamp());
            addStudentEntry.executeUpdate();
            
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
        public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
            connection = DBConnection.getConnection();
            ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
            try {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?)");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while (resultSet.next()) {
                schedules.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
            return schedules;
        }
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule where courseCode = ? and semester = ?");
            dropScheduleByCourse.setString(1, courseCode);
            dropScheduleByCourse.setString(2,semester);
            int rowsAffected = dropScheduleByCourse.executeUpdate();
         
        }
           catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }    
    }
        
 }
 
