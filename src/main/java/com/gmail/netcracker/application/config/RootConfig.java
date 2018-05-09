package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.dao.imp.ChatDaoImpl;
import com.gmail.netcracker.application.dto.dao.interfaces.ChatDao;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.*;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.EventSerializer;
import com.gmail.netcracker.application.utilites.Utilites;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.*;
import org.flywaydb.core.Flyway;
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
import static com.gmail.netcracker.application.utilites.Utilites.parseDateIntoStringFormatWithSeconds;
import static com.gmail.netcracker.application.utilites.Utilites.parseTimeWithSeconds;

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
    public PhotoService photoService() {
        return new PhotoServiceImp();
    }

    @Bean
    public TokenLifeAspect tokenLifeAspect() {
        return new TokenLifeAspect();
    }

    @Bean
    public UserServiceImp userServiceImp() {
        return new UserServiceImp();
    }

//    @Bean
//    UserService userService() {
//        return new UserServiceImp();
//    }

    @Bean
    public VerificationToken verificationToken() {
        return new VerificationToken();
    }

    @Bean
    public User user() {
        return new User();
    }

    @Bean
    public Friend friendship() {
        return new Friend();
    }

    @Bean
    public RegisterValidator registerValidator() {
        return new RegisterValidator();
    }

    @Bean
    public RegisterAndUpdateEventValidator registerEventValidator() {
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
    public Event event() {
        return new Event();
    }

    @Bean
    public FriendService friendServiceFriendService() {
        return new FriendServiceImpl();
    }

    @Bean
    public Note note() {
        return new Note();
    }

    @Bean
    public NoteService noteService() {
        return new NoteServiceImpl();
    }

    @Bean
    public NoteValidator noteValidator() {
        return new NoteValidator();
    }

    @Bean
    public Item item() {
        return new Item();
    }

    @Bean
    public  ItemValidator itemValidator() {
        return new ItemValidator();
    }


    @Bean
    ChatService chatService() {
        return new ChatServiceImpl();
    }

    @Bean
    Chat chat() {
        return new Chat();
    }

    @Bean
    public  LocaleResolver localeResolver() {
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

    @Bean(initMethod = "migrate")
    @Autowired
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations(env.getProperty("flyway.migration.path"));
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RowMapper<EventMessage> eventMessageRowMapper() {
        return (resultSet, i) -> {
            EventMessage eventMessage = new EventMessage();
            eventMessage.setFrom(getString(resultSet, "name"));
            eventMessage.setText(getString(resultSet, "text"));
            eventMessage.setTime(parseDateIntoStringFormatWithSeconds(resultSet.getTimestamp("date")));
            eventMessage.setSenderPhoto(getString(resultSet,"photo"));
            eventMessage.setSenderId(getLong(resultSet,"sender_id"));
            return eventMessage;
        };
    }

    @Bean
    public RowMapper<Chat> chatRowMapper() {
        return (resultSet, i) -> {
            Chat chat = new Chat();
            chat.setChatId(getLong(resultSet, "chat_id"));
            chat.setName(getString(resultSet, "name"));
            chat.setEventId(getLong(resultSet, "event_id"));
            return chat;
        };
    }

    @Bean
    public RowMapper<User> userRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(getLong(resultSet, "user_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setEmail(getString(resultSet, "email"));
            user.setPassword(getString(resultSet, "password"));
            user.setRole(getString(resultSet, "role"));
            user.setPhone(getString(resultSet, "phone"));
            user.setBirthdayDate(parseDateIntoString(getDate(resultSet, "birthdate")));
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
            user.setBirthdayDate(parseDateIntoString(getDate(resultSet, "birthdate")));
            verificationToken.setUser(user);
            return verificationToken;
        };
    }

    @Bean
    public RowMapper<Event> eventRowMapper() {
        return (rs, i) -> {
            Event event = new Event();
            event.setEventId(getLong(rs, "event_id"));
            event.setName(getString(rs, "name"));
            event.setDescription(getString(rs, "description"));
            event.setCreator(getLong(rs, "creator_id"));
            event.setDateStart(getString(rs, "start_date"));
            event.setDateEnd(getString(rs, "end_date"));
            event.setTypeId(getInt(rs, "type_id"));
            event.setDraft(getBoolean(rs, "is_draft"));
            event.setFolder(getInt(rs, "folder_id"));
            event.setWidth(getDouble(rs, "latitude"));
            event.setLongitude(getDouble(rs, "longitude"));
            event.setEventPlaceName(getString(rs, "place_name"));
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
            eventType.setName(getString(resultSet, "name"));
            return eventType;
        };
    }

    @Bean
    public RowMapper<Folder> folderRowMapper() {
        return (resultSet, i) -> {
            Folder folder = new Folder();
            folder.setFolderId(resultSet.getInt("folder_id"));
            folder.setName(resultSet.getString("name"));
            folder.setCreator(resultSet.getLong("creator_id"));
            return folder;
        };
    }

    @Bean
    public RowMapper<Priority> priorityRowMapper() {
        return (resultSet, i) -> {
            Priority priority = new Priority();
            priority.setPriorityId(getInt(resultSet, "priority_id"));
            priority.setName(getString(resultSet, "name"));
            return priority;
        };
    }

    @Bean
    public RowMapper<Participant> participantRowMapper() {
        return (resultSet, i) -> {
            Participant participant = new Participant();
            participant.setEventId(getInt(resultSet, "event_id"));
            participant.setPriority(getInt(resultSet, "priority_id"));
            return participant;
        };
    }

    @Bean
    public RowMapper<Note> noteRowMapper() {
        return (rs, i) -> {
            Note note = new Note();
            note.setNoteId(getLong(rs, "note_id"));
            note.setName(getString(rs, "name"));
            note.setDescription(getString(rs, "description"));
            note.setCreator(getLong(rs, "creator_id"));
            note.setFolder(getInt(rs, "folder_id"));
            return note;
        };
    }

    @Bean
    RowMapper<Note> notesIntoFolderRowMapper() {
        return (rs, i) -> {
            Note note = new Note();
            note.setNoteId(rs.getLong("note_id"));
            note.setName(rs.getString("name"));
            note.setFolder(rs.getInt("folder_id"));
            return note;
        };
    }

    @Bean
    public RowMapper<Friend> friendshipRowMapper() {
        return (resultSet, i) -> {
            Friend friendship = new Friend();
            friendship.setRecipient(getLong(resultSet, "recipient_id"));
            friendship.setSender(getLong(resultSet, "sender_id"));
            friendship.setAccepted(getBoolean(resultSet, "is_accepted"));
            return friendship;
        };
    }

    @Bean
    public RowMapper<Item> itemRowMapper() {
        return (resultSet, i) -> {
            Item item = new Item();
            item.setItemId(getLong(resultSet, "item_id"));
            item.setPersonId(getLong(resultSet, "user_id"));
            item.setBooker(getLong(resultSet, "booker_id"));
            item.setName(getString(resultSet, "name"));
            item.setDescription(getString(resultSet, "description"));
            item.setLink(getString(resultSet, "link"));
            item.setDueDate(getString(resultSet, "due_date"));
            item.setPriority(getInt(resultSet, "priority_id"));
            item.setRoot(getLong(resultSet, "root_id"));
            item.setEvent(getInt(resultSet, "event_id"));
            return item;
        };
    }
}