package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.dao.imp.EventDaoImpl;
import com.gmail.netcracker.application.dto.dao.imp.FriendDaoImpl;
import com.gmail.netcracker.application.dto.dao.imp.UserDaoImp;
import com.gmail.netcracker.application.dto.dao.imp.VerificationTokenDaoImp;
import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.EventServiceImpl;
import com.gmail.netcracker.application.service.imp.FriendServiceImpl;
import com.gmail.netcracker.application.service.imp.UserServiceImp;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.RegisterValidator;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@ComponentScan("com.gmail.netcracker.application.*")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class RootConfig {

    private final Environment env;


    @Bean
    public EmailConcructor emailConcructor() {
        return new EmailConcructor();
    }

    @Autowired
    public RootConfig(Environment env) {
        this.env = env;
    }

    @Bean
    TokenLifeAspect tokenLifeAspect() {
        return new TokenLifeAspect();
    }

    @Bean
    UserServiceImp userServiceImp() {
        return new UserServiceImp();
    }

    @Bean
    UserService userService() {
        return new UserServiceImp();
    }

    @Bean
    VerificationToken verificationToken() {
        return new VerificationToken();
    }

    @Bean
    User user() {
        return new User();
    }

    @Bean
    VerificationTokenDao verificationTokenDao() {
        return new VerificationTokenDaoImp();
    }

    @Bean
    UserDao userDao() {
        return new UserDaoImp();
    }

    @Bean
    RegisterValidator registerValidator() {
        return new RegisterValidator();
    }

    @Bean
    public ResetConfirmPasswordValidator resetConfirmPasswordValidator() {
        return new ResetConfirmPasswordValidator();
    }
    @Bean
    EventDao eventDao() {
        return new EventDaoImpl();
    }

    @Bean
    FriendDao friendDao() {return new FriendDaoImpl(); }

    @Bean
    Event event() {
        return new Event();
    }

    @Bean
    EventService eventService() {
        return new EventServiceImpl();
    }

    @Bean
    FriendService friendServiceriendService() {return new FriendServiceImpl();}

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() throws Exception {
        ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.ENGLISH);
        resourceBundleMessageSource.setBasename("classpath:message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }


    @Bean
    public DataSource
    dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("postgre.driver"));
        driver.setUrl(env.getProperty("postgre.url"));
        driver.setUsername(env.getProperty("postgre.username"));
        driver.setPassword(env.getProperty("postgre.password"));
        return driver;

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
