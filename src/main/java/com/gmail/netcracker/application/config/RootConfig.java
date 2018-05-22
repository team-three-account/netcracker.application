package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.aspects.TokenLifeAspect;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.*;
import com.gmail.netcracker.application.service.interfaces.ChatService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.PhotoService;
import com.gmail.netcracker.application.utilites.*;
import com.gmail.netcracker.application.validation.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

import static com.gmail.netcracker.application.utilites.ResultSetColumnValueExtractor.*;
import static com.gmail.netcracker.application.utilites.Utilities.parseDateToString;
import static com.gmail.netcracker.application.utilites.Utilities.parseDateToStringWithSeconds;

@Configuration
@ComponentScan("com.gmail.netcracker.application.*")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class RootConfig {

    private final Environment env;

    @Bean
    public EmailConstructor emailConcructor() {
        return new EmailConstructor();
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
    public EditUserAccountValidator editUserAccountValidator() {
        return new EditUserAccountValidator();
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
    public FriendService friendService() {
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
    public ItemValidator itemValidator() {
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
    public LocaleResolver localeResolver() {
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

//    @Bean
//    public JobDetailFactoryBean jobDetailFactoryBean() {
//        JobDetailFactoryBean factory = new JobDetailFactoryBean();
//        factory.setJobClass(EventNotificationJob.class);
//        Map<String, Object> params = new HashMap<>();
//        params.put("text", "testText);
//        factory.setJobDataAsMap(params);
//        factory.setGroup("testGroup");
//        factory.setName("testJob");
//        return factory;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean cronTriggerFactoryBean() {
//        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
//        factory.setJobDetail(jobDetailFactoryBean().getObject());
//        factory.setCronExpression("0/5 * * ? * * *");
//        factory.setGroup("testTriggers");
//        factory.setName("testTrigger");
//        return factory;
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        return factory;
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
            eventMessage.setTime(parseDateToStringWithSeconds(getTimestamp(resultSet, "date")));
            eventMessage.setSenderPhoto(getString(resultSet, "photo"));
            eventMessage.setSenderId(getLong(resultSet, "sender_id"));
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
            chat.setState(getBoolean(resultSet, "creator_event"));
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
            user.setBirthdayDate(parseDateToString(getDate(resultSet, "birthdate")));
            user.setPhoto(getString(resultSet, "photo"));
            user.setGender(getString(resultSet, "gender"));
            user.setNotificationPeriodicity(getString(resultSet, "notification_periodicity"));
            user.setNotificationStartDate(getString(resultSet, "notification_start_date"));
            user.setNotificationEndDate(getString(resultSet, "notification_end_date"));
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
            user.setBirthdayDate(parseDateToString(getDate(resultSet, "birthdate")));
            user.setGender(getString(resultSet, "gender"));
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
            event.setTypeId(getLong(rs, "type_id"));
            event.setDraft(getBoolean(rs, "is_draft"));
            event.setWidth(getDouble(rs, "latitude"));
            event.setLongitude(getDouble(rs, "longitude"));
            event.setEventPlaceName(getString(rs, "place_name"));
            event.setPeriodicity(getString(rs, "periodicity"));
            event.setPhoto(getString(rs, "photo"));
            event.setPriorityId(getLong(rs, "priority_id"));
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
            priority.setPriorityId(getLong(resultSet, "priority_id"));
            priority.setName(getString(resultSet, "name"));
            return priority;
        };
    }

    @Bean
    public RowMapper<Participant> participantRowMapper() {
        return (resultSet, i) -> {
            Participant participant = new Participant();
            participant.setEventId(getLong(resultSet, "event_id"));
            participant.setPriority(getLong(resultSet, "priority_id"));
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
            item.setPriority(getLong(resultSet, "priority_id"));
            item.setRoot(getLong(resultSet, "root_id"));
            item.setEvent(getInt(resultSet, "event_id"));
            item.setImage(getString(resultSet, "image"));
            return item;
        };
    }

    @Bean
    public RowMapper<Tag> tagRowMapper() {
        return (resultSet, i) -> {
            Tag tag = new Tag();
            tag.setTagId(getLong(resultSet, "tag_id"));
            tag.setName(getString(resultSet, "name"));
            return tag;
        };
    }

    @Bean
    public Gson gsonTimeline() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new TimelineSerializer())
                .create();
    }

    @Bean
    public Gson gsonEvents() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerializer())
                .create(); }
}