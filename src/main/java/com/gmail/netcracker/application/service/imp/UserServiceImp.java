package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
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
import java.util.logging.Logger;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private JobSchedulingManager jobSchedulingManager;
    private VerificationToken verificationToken;
    private UserDao userDao;
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    public UserServiceImp(JobSchedulingManager jobSchedulingManager, VerificationToken verificationToken, UserDao userDao,
                          VerificationTokenDao verificationTokenDao) {
        this.jobSchedulingManager = jobSchedulingManager;
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

    @Override
    public void updateNotificationSchedule(User user) {
        userDao.updateNotificationsSchedule(user);
        deletePersonalPlanNotificationJob(user.getId());
        schedulePersonalPlanNotificationsJob(user);
    }

    @Override
    public void disableNotifications(Long userId) {
        userDao.disableNotifications(userId);
        deletePersonalPlanNotificationJob(userId);
    }

    private void schedulePersonalPlanNotificationsJob(User user) {
        final String PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX = "personalPlanNotificationJob_";
        final String PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME = "personalPlanNotificationJobs";
        final String PERSONAL_PLAN_NOTIFICATION_TRIGGER_NAME_PREFIX = "personalPlanNotificationTrigger_";
        final String PERSONAL_PLAN_NOTIFICATION_TRIGGER_GROUP_NAME = "personalPlanNotificationTriggers";
        jobSchedulingManager.scheduleJob(user.getId(), null, PersonalPlanNotificationJob.class,
                parseStringToDate(user.getNotificationStartDate()), parseStringToDate(user.getNotificationEndDate()),
                user.getNotificationPeriodicity(),
                PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME,
                PERSONAL_PLAN_NOTIFICATION_TRIGGER_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_TRIGGER_GROUP_NAME);
    }

    private void deletePersonalPlanNotificationJob(Long userId) {
        final String PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX = "personalPlanNotificationJob_";
        final String PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME = "personalPlanNotificationJobs";
        jobSchedulingManager.deleteJob(userId, PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME);
    }
}
