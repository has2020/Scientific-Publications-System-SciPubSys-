package ma.reddit.entities;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class Registration {
    private String username;
    private String password;
    private String email;
    private Timestamp created_at;

    public Registration(){
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public void setPassword(String password) {
        String hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        this.password = hash;
    }

    public String getPassword() {

        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
