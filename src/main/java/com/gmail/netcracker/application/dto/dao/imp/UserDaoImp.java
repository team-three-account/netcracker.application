package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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

    @Value("${sql.user.updateNotificationsSchedule}")
    private String SQL_UPDATE_NOTIFICATIONS_SCHEDULE;

    @Value("${sql.user.getNotificationsSchedule}")
    private String SQL_GET_NOTIFICATIONS_SCHEDULE;

    @Value("${sql.user.role}")
    private String USER_ROLE;

    @Autowired
    private RowMapper<User> userRowMapper;

    @Autowired
    public UserDaoImp(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void saveUser(User user) {
        user.setId(insertEntity(SQL_ADD, PK_COLUMN_NAME,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                USER_ROLE,
                user.getPhone(),
                parseStringToDate(user.getBirthdayDate()),
                user.getPhoto(),
                user.getGender()
        ));
    }

    @Override
    public User findUserByEmail(String email) {
        return findEntity(SQL_FIND_BY_EMAIL, userRowMapper, email);
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
    public User findUserById(Long id) {
        return findEntity(SQL_FIND, userRowMapper, id);
    }

    @Override
    public void updateNotificationsSchedule(User user) {
        updateEntity(SQL_UPDATE_NOTIFICATIONS_SCHEDULE,
                user.getNotificationPeriodicity(),
                parseStringToTimestamp(user.getNotificationStartDate()),
                parseStringToTimestamp(user.getNotificationEndDate()),
                user.getId()
        );
    }


    @Override
    public void disableNotifications(Long userId) {
        updateEntity(SQL_UPDATE_NOTIFICATIONS_SCHEDULE, null, null, null, userId);
    }

}
