package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringIntoDate;

@Repository
public class UserDaoImp extends ModelDao implements UserDao {
    private final String PK_COLUMN_NAME = "person_id";

    private final String SQL_ADD = "user/add.sql";
    private final String SQL_UPDATE = "user/update.sql";
    private final String SQL_FIND_BY_EMAIL = "user/findByEmail.sql";
    private final String SQL_CHANGE_PASSWORD = "user/changePassword.sql";

    private final RowMapper<User> rowMapper;

    @Autowired
    protected UserDaoImp(DataSource dataSource, ResourceLoader resourceLoader, Environment environment,
                         RowMapper<User> rowMapper) {
        super(dataSource, resourceLoader, environment);
        this.rowMapper = rowMapper;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setId(insertEntity(SQL_ADD, PK_COLUMN_NAME,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                "ROLE_USER",
                user.getPhone(),
                parseStringIntoDate(user.getBirthdayDate())
        ));
    }

    @Transactional
    @Override
    public User findUser(String email) {
        return findEntity(SQL_FIND_BY_EMAIL, rowMapper, email);
    }

    @Override
    public void changePassword(String password, String email) {
        updateEntity(SQL_CHANGE_PASSWORD, password, email);
    }

    @Override
    public void updateUser(User user) {
        updateEntity(SQL_UPDATE,
                user.getName(),
                user.getSurname(),
                Utilities.parseStringIntoDate(user.getBirthdayDate()),
                user.getPhone(),
                user.getId());
    }
}
