package dao;
import java.sql.*;
import util.DBConnection;
public class AdminDAO{
    public boolean login(String username,String hashedPassword){
        String sql="SELECT 1 FROM admin WHERE username=? AND password=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,username);
            ps.setString(2,hashedPassword);
            try(ResultSet rs=ps.executeQuery()){
                return rs.next();
            }
        }catch(SQLException e){
            System.out.println("Error during login: "+e.getMessage());
            return false;
        }
    }
}