package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventMessage {

    private String from;
    private String text;
    private String time;
    private Long chatId;
    private Long eventId;
    private String senderPhoto;
    private Long senderId;

    public EventMessage() {
    }

}
