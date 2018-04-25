package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.gmail.netcracker.application.utilites.Utilites.parseDateIntoString;
import static com.gmail.netcracker.application.utilites.Utilites.parseStringIntoDate;

@Repository
public class UserDaoImp extends ModelDao implements UserDao {

    private final String UPDATE = "UPDATE public.\"Person\"\n" +
            "SET name=?, surname=?, birthday=?, phone=?\n" +
            "WHERE \"Person\".person_id=?;";

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


    @Override
    public void updateUser(User user){
        jdbcTemplate.update(UPDATE,
                user.getName(),
                user.getSurname(),
                Utilites.parseStringIntoDate(user.getBirthdayDate()),
                user.getPhone(),
                user.getId());
    }
}
