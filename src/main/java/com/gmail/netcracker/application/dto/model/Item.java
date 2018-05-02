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
    private Date dueDate;
    private int priority;
    private Long root;
}
