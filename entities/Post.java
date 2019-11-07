package ma.reddit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.Timestamp;

public class Post {
    private int id;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private Timestamp created_at;
    private Integer type;
    private String username;
    private String community;
    private Integer score;



    public Post(){
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.score = 0;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return this.id; }

    public void setScore(int score){ this.score=score; }

    public Integer getScore(){ return this.score; }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getUsername() {
        return username;
    }

    public Integer getType() {
        return type;
    }

    public String getCommunity() {
        return community;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public void setCommunity(String community) {
        this.community = community;
    }
}
