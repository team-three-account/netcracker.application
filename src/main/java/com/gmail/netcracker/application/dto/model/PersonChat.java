package com.gmail.netcracker.application.dto.model;

import org.springframework.stereotype.Component;

@Component
public class PersonChat {
    private String personId;
    private String chatId;
    private String lastReadMessage;

    public PersonChat() {
    }

    public PersonChat(String personId, String chatId, String lastReadMessage) {
        this.personId = personId;
        this.chatId = chatId;
        this.lastReadMessage = lastReadMessage;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getLastReadMessage() {
        return lastReadMessage;
    }

    public void setLastReadMessage(String lastReadMessage) {
        this.lastReadMessage = lastReadMessage;
    }

    @Override
    public String toString() {
        return "PersonChat{" +
                "personId=" + personId +
                ", chatId='" + chatId + '\'' +
                ", lastReadMessage='" + lastReadMessage + '\'' +
                '}';
    }
}
