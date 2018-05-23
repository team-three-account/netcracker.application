package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventType {
    private Long typeId;
    private String name;
}
