package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Note {
    private int noteId;
    private String name;
    private String description;
    private Long creator;
    private int folder;
}
