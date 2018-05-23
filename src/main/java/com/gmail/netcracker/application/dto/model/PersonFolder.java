package com.gmail.netcracker.application.dto.model;

import org.springframework.stereotype.Component;

@Component
public class PersonFolder {
    private String personId;
    private String folderId;

    public PersonFolder() {
    }

    public PersonFolder(String personId, String folderId) {
        this.personId = personId;
        this.folderId = folderId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }


    @Override
    public String toString() {
        return "PersonFolder{" +
                "personId=" + personId +
                ", folderId='" + folderId + '\'' +
                '}';
    }
}