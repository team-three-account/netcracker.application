package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.gmail.netcracker.application.utilites.Utilites.parseDateIntoString;
import static com.gmail.netcracker.application.utilites.Utilites.parseStringIntoDate;

@Repository
public class UserDaoImp extends ModelDao implements UserDao {

    @Autowired
    private User user;

    @Transactional
    @Override
    public void saveUser(User user) {
        jdbcTemplate.update("INSERT INTO public.\"Person\" (name, surname, email, password, role, phone,birthday)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",

                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                "ROLE_USER",
                user.getPhone(),
                parseStringIntoDate(user.getBirthdayDate()
                ));
    }

    @Transactional
    @Override
    public User findUser(String email) {
        jdbcTemplate.query("select * from public.\"Person\" where email = " + "'" + email + "'", resultSet -> {
            while (resultSet.next()) {
                user.setId(resultSet.getLong("person_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setPhone(resultSet.getString("phone"));
                user.setBirthdayDate(parseDateIntoString(resultSet.getDate("birthday")));
            }
            return user;
        });
        return user;
    }

    @Override
    public void changePassword(String password, String email) {
        jdbcTemplate.update("update public.\"Person\" set password = ? where email = ? ", password, email);
    }


}
