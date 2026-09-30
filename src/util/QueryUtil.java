package util;
import java.sql.*;
import java.util.*;
public class QueryUtil{
    public static List<String> run(String sql,Object... params){
        List<String> list=new ArrayList<>();
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            for(int i=0;i<params.length;i++){
                ps.setObject(i+1,params[i]);
            }
            try(ResultSet rs=ps.executeQuery()){
                int n=rs.getMetaData().getColumnCount();
                while(rs.next()){
                    StringBuilder sb=new StringBuilder();
                    for(int i=1;i<=n;i++){
                        if(i>1) sb.append(" | ");
                        sb.append(rs.getString(i));
                    }
                    list.add(sb.toString());
                }
            }
        }catch(SQLException e){
            System.out.println("Error running query: "+e.getMessage());
        }
        return list;
    }
}