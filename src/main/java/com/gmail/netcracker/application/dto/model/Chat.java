package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Chat {
    private Long chatId;
    private String name;
    private Long eventId;
    private List<EventMessage> eventMessageList;
    private Boolean state;
    private EventMessage lastMessage;

    public Chat() {
    }

    public Chat(Long chatId, String name, Long eventId) {
        this.chatId = chatId;
        this.name = name;
        this.eventId = eventId;
    }
}
