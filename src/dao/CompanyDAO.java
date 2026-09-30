package dao;
import java.sql.*;
import java.util.*;
import model.Company;
import util.DBConnection;
public class CompanyDAO{
    // CREATE: insert a new company
    public boolean addCompany(Company c){
        String sql="INSERT INTO companies (name, location, contact) VALUES (?, ?, ?)";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,c.getName());
            ps.setString(2,c.getLocation());
            ps.setString(3,c.getContact());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error adding company: "+e.getMessage());
            return false;
        }
    }
    // READ: get all companies
    public List<Company> getAllCompanies(){
        List<Company> list=new ArrayList<>();
        String sql="SELECT * FROM companies";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch(SQLException e){
            System.out.println("Error fetching companies: "+e.getMessage());
        }
        return list;
    }
    // UPDATE: change a company's details
    public boolean updateCompany(Company c){
        String sql="UPDATE companies SET name=?, location=?, contact=? WHERE company_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,c.getName());
            ps.setString(2,c.getLocation());
            ps.setString(3,c.getContact());
            ps.setInt(4,c.getCompanyId());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error updating company: "+e.getMessage());
            return false;
        }
    }
    // DELETE: remove a company
    public boolean deleteCompany(int id){
        String sql="DELETE FROM companies WHERE company_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error deleting company: "+e.getMessage());
            return false;
        }
    }
    // Helper: convert one row into a Company object
    private Company mapRow(ResultSet rs) throws SQLException{
        Company c=new Company();
        c.setCompanyId(rs.getInt("company_id"));
        c.setName(rs.getString("name"));
        c.setLocation(rs.getString("location"));
        c.setContact(rs.getString("contact"));
        return c;
    }
}