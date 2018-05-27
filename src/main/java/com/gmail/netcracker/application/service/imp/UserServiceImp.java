package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.utilites.scheduling.JobSchedulingManager;
import com.gmail.netcracker.application.utilites.scheduling.jobs.PersonalPlanNotificationJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private VerificationToken verificationToken;
    private UserDao userDao;
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    public UserServiceImp(VerificationToken verificationToken, UserDao userDao,
                          VerificationTokenDao verificationTokenDao) {
        this.verificationToken = verificationToken;
        this.userDao = userDao;
        this.verificationTokenDao = verificationTokenDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Logger.getLogger(UserServiceImp.class.getName());
        return userDao.findUserByEmail(email);
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        verificationToken.setId(token);
        verificationToken.setUser(user);
        return verificationTokenDao.create(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationTokenDao.delete(verificationToken);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User getAuthenticatedUser() {
        User user;
        try {
            user = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception ex) {
            ex.printStackTrace();
            user = null;
        }
        return user;
    }

    @Override
    public void changeUserPassword(String password, String email) {
        userDao.changePassword(password, email);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<User> getAllUsers(Long currentId) {
        return userDao.getAllUsers(currentId);
    }

}
