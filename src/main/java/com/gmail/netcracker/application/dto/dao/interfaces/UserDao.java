package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.User;

public interface UserDao {

    void saveUser(User user);

    User findUser(String email);

    void changePassword(String password, String email);

    void updateUser(User user);
}
