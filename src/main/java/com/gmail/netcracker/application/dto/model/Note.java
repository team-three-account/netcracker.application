package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class Note {
    private Long noteId;
    private String name;
    private String description;
    private Long creator;
    private Integer folder;
}
