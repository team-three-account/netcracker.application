package com.gmail.netcracker.application.dao.impl;

import com.gmail.netcracker.application.dao.PersonDao;
import com.gmail.netcracker.application.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonDaoImpl implements PersonDao {
    public static final String REGISTER_ROLE = "ROLE_USER";
    private JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void register(Person person) {

        String sql = "INSERT INTO public.\"Person\"(\n" +
                "\tname, surname, email, date, phone, password, role, city, country)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, person.getName(), person.getSurname(), person.getEmail(), parseTime(person.getDateOfBirthday()),
                person.getPhone(), person.getPassword(), REGISTER_ROLE, person.getCity(), person.getCountry());
    }

    @Override
    public Person getPersonInfo(String email) {
        String sql = "SELECT email,password, role\n" +
                "\tFROM public.\"Person\" WHERE email=?";

        Person personInfo = (Person) jdbcTemplate.queryForObject(sql, new Object[]{email},
                (rs, rowNum) -> {
                    Person person = new Person();
                    person.setEmail(rs.getString("email"));
                    person.setPassword(rs.getString("password"));
                    person.setRole(rs.getString("role"));
                    return person;
                });
        return personInfo;
    }

    private Timestamp parseTime(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(str_date);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
