package com.gmail.netcracker.application.config;


import com.gmail.netcracker.application.service.imp.UserServiceImp;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.gmail.netcracker.application.*")
@PropertySource("classpath:application.properties")
public class RootConfig {

    private final Environment env;

    @Autowired
    public RootConfig(Environment env) {
        this.env = env;
    }

    @Bean
    UserServiceImp userService() {
        return new UserServiceImp();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:validation.properties");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName( "localhost" );
        dataSource.setDatabaseName( "testdb" );
        dataSource.setUser( "testuser" );
        dataSource.setPassword( "password" );
        return dataSource;
    }
}
