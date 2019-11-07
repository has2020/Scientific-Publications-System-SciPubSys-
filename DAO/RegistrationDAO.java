package ma.reddit.DAO;

import ma.reddit.entities.Registration;

import java.sql.*;

public class RegistrationDAO {
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/reddit?autoReconnect=true&useSSL=false", "root", "");
            return conn;
        }catch(SQLException | ClassNotFoundException ex){
            System.out.println(ex);
            return null;
        }
    }
//  checks if email and username are unique
    public String validate(Registration registration) throws SQLException{
        try(Connection conn = this.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("select username, email from users where username=? or email=?")){
                ps.setString(1, registration.getUsername());
                ps.setString(2, registration.getEmail());
                ResultSet rs = ps.executeQuery();
                if(!rs.next()){
                    return null;
                }
                if(rs.getString("username").equals(registration.getUsername())){
                    return "Username already taken";
                }
                return "Email already used";
            }
        }
    }

    public void create(Registration registration) throws SQLException{
        try(Connection conn = getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("insert into users (username, password, email, created_at) values (?, ?, ?, ?)")){
                ps.setString(1, registration.getUsername());
                ps.setString(2, registration.getPassword());
                ps.setString(3, registration.getEmail());
                ps.setTimestamp(4, registration.getCreated_at());

                ps.executeUpdate();
                return;
            }
        }
    }
}
