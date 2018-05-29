package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Notification {
    private User user;
    private Event event;
    private EventMessage lastMessage;
    private Boolean creatorEvent;
    private Long chatId;

}
