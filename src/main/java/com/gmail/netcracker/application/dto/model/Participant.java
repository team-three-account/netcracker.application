package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Participant {
    private Long person;
    private int eventId;
    private Long priority;
    private boolean countdown;
    private boolean isAccepted;
}
