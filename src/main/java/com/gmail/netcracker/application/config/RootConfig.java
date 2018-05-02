package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.*;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.RegisterAndUpdateEventValidator;
import com.gmail.netcracker.application.validation.RegisterValidator;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

import static com.gmail.netcracker.application.utilites.Utilites.parseDateIntoString;

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
    PhotoService photoService() {
        return new PhotoServiceImp();
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
    Friend friendship() {
        return new Friend();
    }

    @Bean
    RegisterValidator registerValidator() {
        return new RegisterValidator();
    }

    @Bean
    RegisterAndUpdateEventValidator registerEventValidator() {
        return new RegisterAndUpdateEventValidator();
    }

    @Bean
    public ResetConfirmPasswordValidator resetConfirmPasswordValidator() {
        return new ResetConfirmPasswordValidator();
    }

    @Bean
    Event event() {
        return new Event();
    }

    @Bean
    FriendService friendServiceFriendService() {
        return new FriendServiceImpl();
    }

    @Bean
    Note note() {
        return new Note();
    }

    @Bean
    NoteService noteService() {
        return new NoteServiceImpl();
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
    public DataSource
    dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("postgres.driver"));
        driver.setUrl(env.getProperty("postgres.url"));
        driver.setUsername(env.getProperty("postgres.username"));
        driver.setPassword(env.getProperty("postgres.password"));
        return driver;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RowMapper<User> userRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("person_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            user.setPhone(resultSet.getString("phone"));
            user.setBirthdayDate(parseDateIntoString(resultSet.getDate("birthday")));
            user.setPhoto(resultSet.getString("photo"));
            return user;
        };
    }

    @Bean
    public RowMapper<VerificationToken> verificationTokenRowMapper() {
        return (resultSet, i) -> {
            VerificationToken verificationToken = new VerificationToken();
            User user = new User();
            verificationToken.setId(resultSet.getString("token_id"));
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            user.setPhone(resultSet.getString("phone"));
            user.setBirthdayDate(parseDateIntoString(resultSet.getDate("birthday")));
            verificationToken.setUser(user);
            return verificationToken;
        };
    }

    @Bean
    public RowMapper<Event> eventRowMapper() {
        return (rs, i) -> {
            Event event = new Event();
            event.setEventId(rs.getInt("event_id"));
            event.setName(rs.getString("name"));
            event.setDescription(rs.getString("description"));
            event.setCreator(rs.getLong("creator"));
            event.setDateStart(rs.getString("start_date"));
            event.setDateEnd(rs.getString("end_date"));
            event.setType(rs.getString("type"));
            event.setDraft(rs.getBoolean("is_draft"));
            event.setFolder(rs.getInt("folder"));
            event.setWidth(rs.getDouble("width"));
            event.setLongitude(rs.getDouble("longitude"));
            event.setEventPlaceName(rs.getString("eventplacename"));
            event.setPeriodicity(rs.getInt("periodicity"));
            event.setPhoto(rs.getString("photo"));
            return event;
        };
    }

    @Bean
    public RowMapper<EventType> eventTypeRowMapper() {
        return (resultSet, i) -> {
            EventType eventType = new EventType();
            eventType.setTypeId(resultSet.getInt("type_id"));
            eventType.setName(resultSet.getString("value"));
            return eventType;
        };
    }

    @Bean
    public RowMapper<Note> noteRowMapper() {
        return (rs, i) -> {
            Note note = new Note();
            note.setNoteId(rs.getInt("note_id"));
            note.setName(rs.getString("name"));
            note.setDescription(rs.getString("description"));
            note.setCreator(rs.getLong("creator"));
            note.setFolder(rs.getInt("folder"));
            return note;
        };
    }

    @Bean
    public RowMapper<Friend> friendshipRowMapper() {
        return (resultSet, i) -> {
            Friend friendship = new Friend();
            friendship.setRecipient(resultSet.getLong("recipient"));
            friendship.setSender(resultSet.getLong("sender"));
            friendship.setAccepted(resultSet.getBoolean("isAccepted"));
            return friendship;
        };
    }

    @Bean
    public RowMapper<User> friendRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("person_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPhoto(resultSet.getString("photo"));
            return user;
        };
    }

    @Bean
    public RowMapper<Item> itemRowMapper() {
        return (resultSet, i) -> {
            Item item = new Item();
            item.setItemId(resultSet.getLong("item_id"));
            item.setPersonId(resultSet.getLong("person"));
            item.setBooker(resultSet.getLong("booker"));
            item.setItemId(resultSet.getLong("name"));
            item.setDescription(resultSet.getString("description"));
            item.setLink(resultSet.getString("link"));
            item.setDueDate(resultSet.getString("due_date"));
            item.setPriority(resultSet.getInt("priority"));
            item.setRoot(resultSet.getLong("root"));
            return item;
        };
    }
}