package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Participant {
    private Long person;
    private Long eventId;
    private Long priority;
    private Boolean countdown;
}
