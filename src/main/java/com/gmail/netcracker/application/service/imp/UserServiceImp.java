package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    VerificationTokenDao verificationTokenDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUser(email);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true,
                Arrays.asList(authority));
        return userDetails;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setId(token);
        verificationToken.setUser(user);
        verificationTokenDao.create(verificationToken);
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
        return userDao.findUser(email);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findUser(auth.getName()) ;
    }

    @Override
    public void changeUserPassword(String password, String email) {
        userDao.changePassword(password,email);
    }


}
