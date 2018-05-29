package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private VerificationToken verificationToken;
    @Autowired
    private UserDao userDao;
    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Logger.getLogger(UserServiceImp.class.getName());
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional
    public VerificationToken createVerificationToken(User user, String token) {
        verificationToken.setId(token);
        verificationToken.setUser(user);
        return verificationTokenDao.create(verificationToken);
    }

    @Override
    @Transactional(readOnly = true)
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    @Transactional
    public void saveRegisteredUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationTokenDao.delete(verificationToken);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void changeUserPassword(String password, String email) {
        userDao.changePassword(password, email);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

}
