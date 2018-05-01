package com.gmail.netcracker.application.dto.model;

public class Chat {
    private String chat_id;
    private String name;
    private String event_id;

    public Chat() {
    }

    public Chat(String chat_id, String name, String event_id) {
        this.chat_id = chat_id;
        this.name = name;
        this.event_id = event_id;
    }


    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chat_id=" + chat_id +
                ", name='" + name + '\'' +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
