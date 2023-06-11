package es.progcipfpbatoi.modelo.dto;

public class Users {

    private String username;
    private String email;
    private Levels level;

    public Users(String username, String email, Levels level) {
        this.username = username;
        this.email = email;
        this.level = level;
    }

    public Users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                '}';
    }
}
