package com.gmail.netcracker.application.utilites;


import com.gmail.netcracker.application.dto.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;



@Component
@Data
public class VerificationToken {

    private String id;

    private User user;

    public VerificationToken(String id, User user) {

        this.id = id;
        this.user = user;

    }

    public VerificationToken() {
    }

}
