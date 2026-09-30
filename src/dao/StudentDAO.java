package dao;
import java.sql.*;
import java.util.*;
import model.Student;
import util.DBConnection;
public class StudentDAO{
    // CREATE: insert a new student
    public boolean addStudent(Student s){
        String sql="INSERT INTO students (name, email, password, phone, department, cgpa, backlogs, skills) "
                   +"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,s.getName());
            ps.setString(2,s.getEmail());
            ps.setString(3,s.getPassword());
            ps.setString(4,s.getPhone());
            ps.setString(5,s.getDepartment());
            ps.setDouble(6,s.getCgpa());
            ps.setInt(7,s.getBacklogs());
            ps.setString(8,s.getSkills());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error adding student: "+e.getMessage());
            return false;
        }
    }
    // READ: get all students
    public List<Student> getAllStudents(){
        List<Student> list = new ArrayList<>();
        String sql="SELECT * FROM students";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
               list.add(mapRow(rs));
            }
        }catch(SQLException e){
           System.out.println("Error fetching students: "+e.getMessage());
        }
        return list;
    }
    // READ: get one student by id (returns null if not found)
    public Student getStudentById(int id){
        String sql="SELECT * FROM students WHERE student_id = ?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch(SQLException e){
           System.out.println("Error finding student: "+e.getMessage());
        }
        return null;
    }
    // UPDATE: change a student's details
    public boolean updateStudent(Student s){
        String sql="UPDATE students SET name=?, phone=?, department=?, cgpa=?, backlogs=?, skills=? "+"WHERE student_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1, s.getName());
            ps.setString(2, s.getPhone());
            ps.setString(3, s.getDepartment());
            ps.setDouble(4, s.getCgpa());
            ps.setInt(5, s.getBacklogs());
            ps.setString(6, s.getSkills());
            ps.setInt(7, s.getStudentId());
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
           System.out.println("Error updating student: "+e.getMessage());
           return false;
        }
    }
    // DELETE: remove a student
    public boolean deleteStudent(int id){
        String sql="DELETE FROM students WHERE student_id = ?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
           System.out.println("Error deleting student: "+e.getMessage());
           return false;
        }
    }
    // Helper: convert one row of the ResultSet into a Student object
    private Student mapRow(ResultSet rs) throws SQLException{
        Student s=new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setPhone(rs.getString("phone"));
        s.setDepartment(rs.getString("department"));
        s.setCgpa(rs.getDouble("cgpa"));
        s.setBacklogs(rs.getInt("backlogs"));
        s.setSkills(rs.getString("skills"));
        return s;
    }
    // LOGIN: returns the student if email and hashed password match, else null
    public Student login(String email,String hashedPassword){
        String sql="SELECT * FROM students WHERE email=? AND password=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,email);
            ps.setString(2,hashedPassword);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch(SQLException e){
            System.out.println("Error during login: "+e.getMessage());
        }
        return null;
    }
    // SEARCH: by name or department
    public List<Student> searchStudents(String keyword){
        List<Student> list=new ArrayList<>();
        String sql="SELECT * FROM students WHERE name LIKE ? OR department LIKE ?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,"%"+keyword+"%");
            ps.setString(2,"%"+keyword+"%");
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    list.add(mapRow(rs));
                }
            }
        }catch(SQLException e){
            System.out.println("Error searching students: "+e.getMessage());
        }
        return list;
    }
}