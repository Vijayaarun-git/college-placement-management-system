package dao;
import java.sql.*;
import util.DBConnection;
public class PlacementDAO{
    // CREATE: record a placement, package is copied from the job
    public boolean addPlacement(int studentId,int jobId){
        String sql="INSERT INTO placements (student_id, job_id, package_lpa, placed_date) "
                   +"SELECT ?, ?, package_lpa, CURDATE() FROM jobs WHERE job_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,studentId);
            ps.setInt(2,jobId);
            ps.setInt(3,jobId);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error adding placement: "+e.getMessage());
            return false;
        }
    }
    // CHECK: is this student already placed
    public boolean isPlaced(int studentId){
        String sql="SELECT 1 FROM placements WHERE student_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,studentId);
            try(ResultSet rs=ps.executeQuery()){
                return rs.next();
            }
        }catch(SQLException e){
            System.out.println("Error checking placement: "+e.getMessage());
            return false;
        }
    }
}