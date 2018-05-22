package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Data
public class User implements UserDetails {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private transient String confirmPassword;
    private String role;
    private String birthdayDate;
    private String phone;
    private String photo;
    private MultipartFile photoFile;
    private String gender;
    private String notificationPeriodicity;
    private String notificationStartDate;
    private String notificationEndDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(
                new SimpleGrantedAuthority(
                        getRole()
                )
        );
        return roles;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}