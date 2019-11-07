package ma.reddit.DAO;

import ma.reddit.entities.Auth;
import ma.reddit.entities.User;

import java.sql.*;

public class AuthDAO {
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/reddit?autoReconnect=true&useSSL=false", "root", "");
            return conn;
        }catch(SQLException | ClassNotFoundException ex){
            System.out.println(ex);
            return null;
            // TODO when null send default 404 error page
        }
    }

    public User check(Auth auth)throws SQLException{
        try(Connection conn = this.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("select id, username, image from users where username=? and password=?")){
                ps.setString(1, auth.getUsername());
                ps.setString(2, auth.getPassword());

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setImage(rs.getString("image"));
                    user.setId(rs.getInt("id"));

                    return user;
                }
                return null;
            }
        }
    }




}
