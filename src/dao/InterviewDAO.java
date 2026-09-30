package dao;
import java.sql.*;
import util.DBConnection;
public class InterviewDAO{
    // CREATE: schedule an interview for an application
    public boolean scheduleInterview(int applicationId,String date,String time,String venue){
        String sql="INSERT INTO interviews (application_id, interview_date, interview_time, venue) VALUES (?, ?, ?, ?)";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,applicationId);
            ps.setDate(2,Date.valueOf(date));
            ps.setTime(3,Time.valueOf(time));
            ps.setString(4,venue);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error scheduling interview: "+e.getMessage());
            return false;
        }
    }
    // UPDATE: save the interview result
    public boolean updateResult(int interviewId,String result){
        String sql="UPDATE interviews SET result=? WHERE interview_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,result);
            ps.setInt(2,interviewId);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error updating result: "+e.getMessage());
            return false;
        }
    }
    // READ: application id of an interview (-1 if not found)
    public int getApplicationIdByInterview(int interviewId){
        String sql="SELECT application_id FROM interviews WHERE interview_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,interviewId);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt("application_id");
                }
            }
        }catch(SQLException e){
            System.out.println("Error finding interview: "+e.getMessage());
        }
        return -1;
    }
}