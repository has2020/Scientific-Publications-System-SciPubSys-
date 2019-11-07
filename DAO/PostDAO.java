package ma.reddit.DAO;

import ma.reddit.entities.Post;
import ma.reddit.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

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

        public void create(Post post) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("insert into posts(title, content, created_at, username, type, community, score) values(?, ?, ?, ?, ?, ?, ?)")){
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getContent());
                    ps.setTimestamp(3, post.getCreated_at());
                    ps.setString(4, post.getUsername());
                    ps.setInt(5, post.getType());
                    ps.setString(6, post.getCommunity());
                    ps.setInt(7, post.getScore());

                    ps.executeUpdate();
                }
            }
        }

        public void update(Post post){}

        public void delete(int id){}

        public User getPost(int id){ return null; }

        public List<Post> getByCommunity(int id, int limit) throws SQLException{
            try(Connection conn = this.getConnection()){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from posts where community = " + id + " limit " + limit);

                ArrayList<Post> posts = new ArrayList<Post>();

                while(rs.next()){
                    Post post = new Post();

                    post.setTitle(rs.getString("title"));
                    post.setId(rs.getInt("id"));
                    post.setContent(rs.getString("content"));
                    post.setUsername(rs.getString("username"));
                    post.setCreated_at(rs.getTimestamp("created_at"));
                    post.setType(rs.getInt("type"));
                    post.setScore(rs.getInt("score"));
                    post.setCommunity(rs.getString("community"));


                    posts.add(post);
                }

                return posts;

            }
        }
        public List<Post> getByCommunity(String community, int limit) throws SQLException{
            try(Connection conn = this.getConnection()){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from posts where community like '" + community + "' limit " + limit);

                ArrayList<Post> posts = new ArrayList<Post>();

                while(rs.next()){
                    Post post = new Post();

                    post.setTitle(rs.getString("title"));
                    post.setId(rs.getInt("id"));
                    post.setContent(rs.getString("content"));
                    post.setUsername(rs.getString("username"));
                    post.setCreated_at(rs.getTimestamp("created_at"));
                    post.setType(rs.getInt("type"));
                    post.setScore(rs.getInt("score"));
                    post.setCommunity(rs.getString("community"));


                    posts.add(post);
                }

                return posts;

            }
        }

        public List<Post> getByCommunity(int id, int offset, int limit) throws SQLException{
            try(Connection conn = this.getConnection()){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from posts where community = " + id + " limit " + limit + " offset " + offset);

                ArrayList<Post> posts = new ArrayList<Post>();

                while(rs.next()){
                    Post post = new Post();

                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setUsername(rs.getString("username"));
                    post.setCreated_at(rs.getTimestamp("created_at"));
                    post.setType(rs.getInt("type"));
                    post.setScore(rs.getInt("score"));
                    post.setId(rs.getInt("id"));
                    post.setCommunity(rs.getString("community"));


                    posts.add(post);
                }

                return posts;

            }
        }
        public List<Post> getByCommunity(String community, int offset, int limit) throws SQLException{
            try(Connection conn = this.getConnection()){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from posts where community like '" + community + "' limit " + limit + " offset " + offset);

                ArrayList<Post> posts = new ArrayList<Post>();

                while(rs.next()){
                    Post post = new Post();

                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setUsername(rs.getString("username"));
                    post.setCreated_at(rs.getTimestamp("created_at"));
                    post.setType(rs.getInt("type"));
                    post.setScore(rs.getInt("score"));
                    post.setId(rs.getInt("id"));
                    post.setCommunity(rs.getString("community"));


                    posts.add(post);
                }

                return posts;

            }
        }

        public List<Post> getByCommunity(int id) throws SQLException{
            try(Connection conn = this.getConnection()){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select id, title, content, username, created_at, type, score from posts where community = " + id + " order by created_at asc");
                ArrayList<Post> posts = new ArrayList<Post>();

                while(rs.next()){
                    Post post = new Post();

                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setUsername(rs.getString("username"));
                    post.setCreated_at(rs.getTimestamp("created_at"));
                    post.setType(rs.getInt("type"));
                    post.setScore(rs.getInt("score"));

                    posts.add(post);
                }

                return posts;
            }
        }

//increments the number of votes
        public void increment(int id) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("update posts set score=score+1 where id=?")){
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
        }
//decrements the number of votes
        public void decrement(int id) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("update posts set score=score-1 where id=?")){
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
        }
//gets the number of votes
        public Integer getScore(int id) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("select score from posts where id=?")){
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("score");
                }
            }
        }

        public List<Post> getByTitle(String title, int limit) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("select * from posts where title like ? limit " + limit)){
                    ps.setString(1, "%" + title + "%");
                    ResultSet rs = ps.executeQuery();
                    List<Post> posts = new ArrayList<Post>();
                    while(rs.next()){
                        Post post = new Post();
                        post.setTitle(rs.getString("title"));
                        post.setContent(rs.getString("content"));
                        post.setUsername(rs.getString("username"));
                        post.setId(rs.getInt("id"));
                        post.setCommunity(rs.getString("community"));
                        post.setScore(rs.getInt("score"));
                        post.setCreated_at(rs.getTimestamp("created_at"));

                        posts.add(post);
                    }
                    return (posts.isEmpty())? null : posts;
                }
            }
        }
        public List<Post> getByTitle(String title, int limit, int offset) throws SQLException{
            try(Connection conn = this.getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("select * from posts where title like ? limit " + limit + " offset " + offset )){
                    ps.setString(1, "%" + title + "%");
                    ResultSet rs = ps.executeQuery();
                    List<Post> posts = new ArrayList<Post>();
                    while(rs.next()){
                        Post post = new Post();
                        post.setTitle(rs.getString("title"));
                        post.setContent(rs.getString("content"));
                        post.setUsername(rs.getString("username"));
                        post.setId(rs.getInt("id"));
                        post.setCommunity(rs.getString("community"));
                        post.setScore(rs.getInt("score"));
                        post.setCreated_at(rs.getTimestamp("created_at"));

                        posts.add(post);
                    }
                    return (posts.isEmpty())? null : posts;
                }
            }
        }

        public List<Post> getByUser(int id, int limit){return null;}

        public List<Post> getByUser(String username, int limit){return null;}
}

