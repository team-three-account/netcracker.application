package com.gmail.netcracker.application.dao;

import com.gmail.netcracker.application.model.Person;

public interface PersonDao {
    void register(Person person);
    Person getPersonInfo(String username);
}
