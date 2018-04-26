package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private transient String confirmPassword;
    private String role;
    private String birthdayDate;
    private String phone;
}