package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.dao.imp.UserDaoImp;
import com.gmail.netcracker.application.dto.dao.imp.VerificationTokenDaoImp;
import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao;
import com.gmail.netcracker.application.service.imp.UserServiceImp;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.RegisterValidator;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setUrl("jdbc:postgresql://ec2-54-217-217-142.eu-west-1.compute.amazonaws.com/dccj5b64fcv5hl?sslmode=require");
        driver.setUsername("lwxcegckywakkn");
        driver.setPassword("373f20167c234c3f771777da2f715f5f20e6274d9f014cc03daebc00485dc867");
        return driver;

    }


}
