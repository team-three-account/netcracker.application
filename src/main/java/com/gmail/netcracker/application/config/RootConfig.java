package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.*;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.EventSerializer;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.NoteValidator;
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

import static com.gmail.netcracker.application.utilites.ResultSetColumnValueExtractor.*;
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
    public EventSerializer eventSerializer() {
        return new EventSerializer();
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
    NoteValidator noteValidator() {
        return new NoteValidator();
    }

    @Bean
    Item item() {
        return new Item();
    }

    @Bean
    ItemService itemService() {
        return new ItemServiceImpl();
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
        driver.setDriverClassName(env.getProperty("postgres.driver"));
        driver.setUrl(env.getProperty("postgres.url"));
        driver.setUsername(env.getProperty("postgres.username"));
        driver.setPassword(env.getProperty("postgres.password"));
        return driver;
    }

//    @Bean(initMethod = "migrate")
//    @Autowired
//    public Flyway flyway(DataSource dataSource) {
//        Flyway flyway = new Flyway();
//        flyway.setBaselineOnMigrate(true);
//        flyway.setLocations(env.getProperty("flyway.migration.path"));
//        flyway.setDataSource(dataSource);
//        return flyway;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RowMapper<User> userRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(getLong(resultSet, "person_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setEmail(getString(resultSet, "email"));
            user.setPassword(getString(resultSet, "password"));
            user.setRole(getString(resultSet, "role"));
            user.setPhone(getString(resultSet, "phone"));
            user.setBirthdayDate(parseDateIntoString(getDate(resultSet, "birthday")));
            user.setPhoto(getString(resultSet, "photo"));
            return user;
        };
    }

    @Bean
    public RowMapper<VerificationToken> verificationTokenRowMapper() {
        return (resultSet, i) -> {
            VerificationToken verificationToken = new VerificationToken();
            User user = new User();
            verificationToken.setId(getString(resultSet, "token_id"));
            user.setId(getLong(resultSet, "user_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setEmail(getString(resultSet, "email"));
            user.setPassword(getString(resultSet, "password"));
            user.setRole(getString(resultSet, "role"));
            user.setPhone(getString(resultSet, "phone"));
            user.setBirthdayDate(parseDateIntoString(getDate(resultSet, "birthday")));
            verificationToken.setUser(user);
            return verificationToken;
        };
    }

    @Bean
    public RowMapper<Event> eventRowMapper() {
        return (rs, i) -> {
            Event event = new Event();
            event.setEventId(getInt(rs, "event_id"));
            event.setName(getString(rs, "name"));
            event.setDescription(getString(rs, "description"));
            event.setCreator(getLong(rs, "creator"));
            event.setDateStart(getString(rs, "start_date"));
            event.setDateEnd(getString(rs, "end_date"));
            event.setType(getString(rs, "type"));
            event.setDraft(getBoolean(rs, "is_draft"));
            event.setFolder(getInt(rs, "folder"));
            event.setWidth(getDouble(rs, "width"));
            event.setLongitude(getDouble(rs, "longitude"));
            event.setEventPlaceName(getString(rs, "eventplacename"));
            event.setPeriodicity(getString(rs, "periodicity"));
            event.setPhoto(getString(rs, "photo"));
            return event;
        };
    }

    @Bean
    public RowMapper<EventType> eventTypeRowMapper() {
        return (resultSet, i) -> {
            EventType eventType = new EventType();
            eventType.setTypeId(getInt(resultSet, "type_id"));
            eventType.setName(getString(resultSet, "value"));
            return eventType;
        };
    }

    @Bean
    public RowMapper<Priority> priorityRowMapper() {
        return (resultSet, i) -> {
            Priority priority = new Priority();
            priority.setPriorityId(getInt(resultSet, "priority_id"));
            priority.setName(getString(resultSet, "value"));
            return priority;
        };
    }

    @Bean
    public RowMapper<Participant> participantRowMapper() {
        return (resultSet, i) -> {
            Participant participant = new Participant();
            participant.setEventId(getInt(resultSet, "event"));
            participant.setPriority(getInt(resultSet, "priority"));
            return participant;
        };
    }

    @Bean
    public RowMapper<Note> noteRowMapper() {
        return (rs, i) -> {
            Note note = new Note();
            note.setNoteId(getInt(rs, "note_id"));
            note.setName(getString(rs, "name"));
            note.setDescription(getString(rs, "description"));
            note.setCreator(getLong(rs, "creator"));
            note.setFolder(getInt(rs, "folder"));
            return note;
        };
    }

    @Bean
    public RowMapper<Friend> friendshipRowMapper() {
        return (resultSet, i) -> {
            Friend friendship = new Friend();
            friendship.setRecipient(getLong(resultSet, "recipient"));
            friendship.setSender(getLong(resultSet, "sender"));
            friendship.setAccepted(getBoolean(resultSet, "isAccepted"));
            return friendship;
        };
    }

    @Bean
    public RowMapper<User> friendRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(getLong(resultSet, "person_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setPhoto(getString(resultSet, "photo"));
            return user;
        };
    }

    @Bean
    public RowMapper<Item> itemRowMapper() {
        return (resultSet, i) -> {
            Item item = new Item();
            item.setItemId(getLong(resultSet, "item_id"));
            item.setPersonId(getLong(resultSet, "person"));
            item.setBooker(getLong(resultSet, "booker"));
            item.setName(getString(resultSet, "name"));
            item.setDescription(getString(resultSet, "description"));
            item.setLink(getString(resultSet, "link"));
            item.setDueDate(getDate(resultSet, "due_date"));
            item.setPriority(getInt(resultSet, "priority"));
            item.setRoot(getLong(resultSet, "root"));
            return item;
        };
    }
}