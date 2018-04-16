package com.gmail.netcracker.application.config;


import com.gmail.netcracker.application.service.imp.UserServiceImp;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.gmail.netcracker.application.service.imp*")
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;

    private final UserServiceImp userService;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserServiceImp userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userService);
    }

    @Autowired
    protected void registerGlobalAuthentication(AuthenticationManagerBuilder registry) throws Exception {
        registry
                .userDetailsService(userService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/account").hasRole("USER")
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/account")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .csrf().disable()
                .rememberMe();

    }


}


