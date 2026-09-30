package dao;
import java.sql.*;
import java.util.*;
import model.Application;
import util.DBConnection;
public class ApplicationDAO{
    // CREATE: student applies for a job (date is set by MySQL)
    public boolean addApplication(int studentId,int jobId){
        String sql="INSERT INTO applications (student_id, job_id, status, applied_date) VALUES (?, ?, 'Applied', CURDATE())";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,studentId);
            ps.setInt(2,jobId);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error adding application: "+e.getMessage());
            return false;
        }
    }
    // CHECK: has this student already applied for this job
    public boolean hasApplied(int studentId,int jobId){
        String sql="SELECT 1 FROM applications WHERE student_id=? AND job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,studentId);
            ps.setInt(2,jobId);
            try(ResultSet rs=ps.executeQuery()){
                return rs.next();
            }
        }catch(SQLException e){
            System.out.println("Error checking application: "+e.getMessage());
            return false;
        }
    }
    // READ: all applications of one student
    public List<Application> getApplicationsByStudent(int studentId){
        List<Application> list=new ArrayList<>();
        String sql="SELECT * FROM applications WHERE student_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,studentId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    list.add(mapRow(rs));
                }
            }
        }catch(SQLException e){
            System.out.println("Error fetching applications: "+e.getMessage());
        }
        return list;
    }
    // READ: all applications for one job
    public List<Application> getApplicationsByJob(int jobId){
        List<Application> list=new ArrayList<>();
        String sql="SELECT * FROM applications WHERE job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,jobId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    list.add(mapRow(rs));
                }
            }
        }catch(SQLException e){
            System.out.println("Error fetching applications: "+e.getMessage());
        }
        return list;
    }
    // READ: every application
    public List<Application> getAllApplications(){
        List<Application> list=new ArrayList<>();
        String sql="SELECT * FROM applications";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch(SQLException e){
            System.out.println("Error fetching applications: "+e.getMessage());
        }
        return list;
    }
    // UPDATE: change the status of an application
    public boolean updateStatus(int applicationId,String status){
        String sql="UPDATE applications SET status=? WHERE application_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,status);
            ps.setInt(2,applicationId);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error updating status: "+e.getMessage());
            return false;
        }
    }
    // Helper: convert one row into an Application object
    private Application mapRow(ResultSet rs) throws SQLException{
        Application a=new Application();
        a.setApplicationId(rs.getInt("application_id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setJobId(rs.getInt("job_id"));
        a.setStatus(rs.getString("status"));
        a.setAppliedDate(rs.getString("applied_date"));
        return a;
    }
    // READ: get one application by id (returns null if not found)
    public Application getApplicationById(int id){
        String sql="SELECT * FROM applications WHERE application_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch(SQLException e){
            System.out.println("Error finding application: "+e.getMessage());
        }
        return null;
    }
}