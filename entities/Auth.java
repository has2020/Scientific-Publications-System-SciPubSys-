package ma.reddit.entities;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Auth {
    private String username;
    private String email;
    private String password;

    public Auth(){}

    public Auth(String username, String password){
        this.setPassword(password);
        this.setUsername(username);
    }
    public String getUsername() {
//        trim
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        this.password = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
