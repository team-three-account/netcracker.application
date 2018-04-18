package com.gmail.netcracker.application.utilites;


import com.gmail.netcracker.application.dto.model.User;
import org.springframework.stereotype.Component;



@Component
public class VerificationToken {

    private String id;

    private User user;

    public VerificationToken(String id, User user) {

        this.id = id;
        this.user = user;

    }

    public VerificationToken() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                ", user=" + user +
                '}';
    }
}
