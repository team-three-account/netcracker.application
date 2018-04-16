package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Repository
public class UserDaoImp extends ModelDao implements UserDao {

    @Transactional
    @Override
    public void saveUser(User user) {
        jdbcTemplate.update("INSERT INTO person (person_id,name, surname, email, password, role, phone)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                "ROLE_USER",
                user.getPhone()
        );
    }

    @Transactional
    @Override
    public User findUser(String email) {
        User user = new User();
        jdbcTemplate.query("select * from person where email = " + "'" + email + "'", resultSet -> {
            while (resultSet.next()) {
                user.setId(resultSet.getString("person_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setPhone(resultSet.getString("phone"));
            }
            return user;
        });
        return user;
    }

    @Override
    public void changePassword(String password, String email) {
        jdbcTemplate.update("update person set password = ? where email = ? ", password,email);
    }


    private Timestamp parseTime(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(str_date);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
