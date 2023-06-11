package es.progcipfpbatoi.modelo.dto;

import java.security.PrivilegedAction;
import java.time.LocalDateTime;
import java.util.Date;

public class Partida {

    private int id;
    private Users users;
    private int points;
    private Levels levels;
    private Date partidadate;


    public Partida(int id, Users users, int points, Levels levels, Date partidadate) {
        this.id = id;
        this.users = users;
        this.points = points;
        this.levels = levels;
        this.partidadate = partidadate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Levels getLevels() {
        return levels;
    }

    public void setLevels(Levels levels) {
        this.levels = levels;
    }

    public Date getPartidadate() {
        return partidadate;
    }

    public void setPartidadate(Date partidadate) {
        this.partidadate = partidadate;
    }

    @Override
    public String toString() {
        return users.getUsername() +
                " Puntos : " + points;
    }
}
