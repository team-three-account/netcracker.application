package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dao.PersonDao;
import com.gmail.netcracker.application.model.Person;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private PersonDao personDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person personInfo = personDao.getPersonInfo(email);
        GrantedAuthority authority = new SimpleGrantedAuthority(personInfo.getRole());
        UserDetails userDetails = (UserDetails) new User(personInfo.getEmail(), personInfo.getPassword(),
                true, true, true, true,
                Arrays.asList(authority));
        return userDetails;
    }
}
