package com.gmail.netcracker.application.dto.model;

public class PersonFolder {
    private String person_id;
    private String folder_id;

    public PersonFolder() {
    }

    public PersonFolder(String person_id, String folder_id) {
        this.person_id = person_id;
        this.folder_id = folder_id;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
    }


    @Override
    public String toString() {
        return "PersonFolder{" +
                "person_id=" + person_id +
                ", folder_id='" + folder_id + '\'' +
                '}';
    }
}