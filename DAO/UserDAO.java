package ma.reddit.DAO;

import ma.reddit.entities.Community;
import ma.reddit.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


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

    public void update(User user){}
    public User getUser(Integer id)throws SQLException{
        if(id == null){return null;}
        try(Connection conn = this.getConnection()){
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id =" + id);
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
    public ArrayList<Community> getCommunities(int id) throws SQLException{
        ArrayList<Community> communities = new ArrayList<Community>();
        try(Connection conn = this.getConnection()){
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id, name from communities co join subscription su on co.user_id = su.user_id");
            while(rs.next()){
                Community community = new Community();
                community.setName(rs.getString("name"));
                community.setId(rs.getInt("id"));

                communities.add(community);
            }

            if(communities.size() == 0){return null;}
            return communities;
        }
    }
    public User getUser(String username){return null;}

}
