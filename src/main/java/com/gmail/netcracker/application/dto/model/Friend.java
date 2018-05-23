package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Friend {
    private Long sender;
    private Long recipient;
    private Boolean isAccepted;
}
