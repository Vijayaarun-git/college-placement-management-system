package dao;
import java.sql.*;
import java.util.*;
import model.Job;
import util.DBConnection;
public class JobDAO{
    // CREATE: insert a new job
    public boolean addJob(Job j){
        String sql="INSERT INTO jobs (company_id, role, package_lpa, min_cgpa, max_backlogs, allowed_departments, required_skills) "
                   +"VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,j.getCompanyId());
            ps.setString(2,j.getRole());
            ps.setDouble(3,j.getPackageLpa());
            ps.setDouble(4,j.getMinCgpa());
            ps.setInt(5,j.getMaxBacklogs());
            ps.setString(6,j.getAllowedDepartments());
            ps.setString(7,j.getRequiredSkills());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error adding job: "+e.getMessage());
            return false;
        }
    }
    // READ: get all jobs
    public List<Job> getAllJobs(){
        List<Job> list=new ArrayList<>();
        String sql="SELECT * FROM jobs";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch(SQLException e){
            System.out.println("Error fetching jobs: "+e.getMessage());
        }
        return list;
    }
    // READ: get one job by id (returns null if not found)
    public Job getJobById(int id){
        String sql="SELECT * FROM jobs WHERE job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch(SQLException e){
            System.out.println("Error finding job: "+e.getMessage());
        }
        return null;
    }
    // READ: get all jobs of one company
    public List<Job> getJobsByCompany(int companyId){
        List<Job> list=new ArrayList<>();
        String sql="SELECT * FROM jobs WHERE company_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,companyId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    list.add(mapRow(rs));
                }
            }
        }catch(SQLException e){
            System.out.println("Error fetching jobs: "+e.getMessage());
        }
        return list;
    }
    // UPDATE: change job details and eligibility criteria
    public boolean updateJob(Job j){
        String sql="UPDATE jobs SET role=?, package_lpa=?, min_cgpa=?, max_backlogs=?, allowed_departments=?, required_skills=? WHERE job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,j.getRole());
            ps.setDouble(2,j.getPackageLpa());
            ps.setDouble(3,j.getMinCgpa());
            ps.setInt(4,j.getMaxBacklogs());
            ps.setString(5,j.getAllowedDepartments());
            ps.setString(6,j.getRequiredSkills());
            ps.setInt(7,j.getJobId());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error updating job: "+e.getMessage());
            return false;
        }
    }
    // DELETE: remove a job
    public boolean deleteJob(int id){
        String sql="DELETE FROM jobs WHERE job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error deleting job: "+e.getMessage());
            return false;
        }
    }
    // Helper: convert one row into a Job object
    private Job mapRow(ResultSet rs) throws SQLException{
        Job j=new Job();
        j.setJobId(rs.getInt("job_id"));
        j.setCompanyId(rs.getInt("company_id"));
        j.setRole(rs.getString("role"));
        j.setPackageLpa(rs.getDouble("package_lpa"));
        j.setMinCgpa(rs.getDouble("min_cgpa"));
        j.setMaxBacklogs(rs.getInt("max_backlogs"));
        j.setAllowedDepartments(rs.getString("allowed_departments"));
        j.setRequiredSkills(rs.getString("required_skills"));
        return j;
    }
}