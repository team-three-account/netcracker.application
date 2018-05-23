package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Folder {
    private int folderId;
    private String name;
    private Long creator;
}
