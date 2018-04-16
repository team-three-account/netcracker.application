package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserDao {

    void saveUser(User user);

    User findUser(String email);

    void changePassword(String password, String email);
}
