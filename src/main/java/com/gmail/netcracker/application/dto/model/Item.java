package com.gmail.netcracker.application.dto.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Item {
    private Long itemId;
    private Long personId;
    private Long booker;
    private String name;
    private String description;
    private String link;
    private String dueDate;
    private Long priority;
    private Long root;
    private Integer event;
}
