package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Friend {
    private Long sender;
    private Long recipient;
    private boolean isAccepted;
}
