package com.gmail.netcracker.application.utilites;


import com.gmail.netcracker.application.dto.model.User;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    private String id;

    private String token;

    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;

    }

    public VerificationToken() {
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
