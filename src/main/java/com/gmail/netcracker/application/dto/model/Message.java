package com.gmail.netcracker.application.dto.model;

public class Message {
    private String message_id;
    private String text;
    private String date;
    private String sender;
    private String chat;

    public Message() {
    }

    public Message(String message_id, String text, String date, String sender, String chat) {
        this.message_id = message_id;
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.chat = chat;
    }


    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", sender='" + sender + '\'' +
                ", chat='" + chat + '\'' +
                '}';
    }
}
