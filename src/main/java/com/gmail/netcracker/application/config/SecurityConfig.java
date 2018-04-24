package com.gmail.netcracker.application.config;


import com.gmail.netcracker.application.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.gmail.netcracker.application.service.imp*")
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserServiceImp userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder builder)
            throws Exception {
        builder.authenticationProvider(authProvider());


    }

    @Autowired
    protected void registerGlobalAuthentication(AuthenticationManagerBuilder registry, PasswordEncoder passwordEncoder) throws Exception {
        registry
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/account/**").hasAnyRole("USER")
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/account")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .csrf().disable();

    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}


