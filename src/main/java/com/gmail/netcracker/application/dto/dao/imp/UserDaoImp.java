package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;
import static com.gmail.netcracker.application.utilites.Utilities.parseStringToTimestamp;

@Repository
public class UserDaoImp extends ModelDao implements UserDao {
    @Value("${sql.user.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.user.add}")
    private String SQL_ADD;

    @Value("${sql.user.update}")
    private String SQL_UPDATE;

    @Value("${sql.user.find}")
    private String SQL_FIND;

    @Value("${sql.user.findByEmail}")
    private String SQL_FIND_BY_EMAIL;

    @Value("${sql.user.changePassword}")
    private String SQL_CHANGE_PASSWORD;

    @Value("${sql.user.getAllUsers}")
    private String SQL_GET_ALL_USERS;

    private final RowMapper<User> rowMapper;
    @Autowired
    public UserDaoImp(DataSource dataSource, RowMapper<User> rowMapper) {
        super(dataSource);
        this.rowMapper = rowMapper;
    }

    //TODO don't hardcode role!!!
    @Override
    public void saveUser(User user) {
        user.setId(insertEntity(SQL_ADD, PK_COLUMN_NAME,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                "ROLE_USER",
                user.getPhone(),
                parseStringToTimestamp(user.getBirthdayDate()),
                user.getPhoto(),
                user.getGender()
        ));
    }

    @Override
    public User findUserByEmail(String email) {
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
                user.getPhone(),
                parseStringToDate(user.getBirthdayDate()),
                user.getPhoto(),
                user.getId());
    }

    @Override
    public List<User> getAllUsers(Long currentId) {
        return findEntityList(SQL_GET_ALL_USERS, rowMapper, currentId);
    }

    @Override
    public User findUserById(Long id) {
        return findEntity(SQL_FIND, rowMapper, id);
    }
}
