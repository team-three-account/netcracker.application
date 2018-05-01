package com.gmail.netcracker.application.dto.model;

public class PersonChat {
    private String person_id;
    private String chat_id;
    private String last_read_message;

    public PersonChat() {
    }

    public PersonChat(String person_id, String chat_id, String last_read_message) {
        this.person_id = person_id;
        this.chat_id = chat_id;
        this.last_read_message = last_read_message;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getLast_read_message() {
        return last_read_message;
    }

    public void setLast_read_message(String last_read_message) {
        this.last_read_message = last_read_message;
    }

    @Override
    public String toString() {
        return "PersonChat{" +
                "person_id=" + person_id +
                ", chat_id='" + chat_id + '\'' +
                ", last_read_message='" + last_read_message + '\'' +
                '}';
    }
}
