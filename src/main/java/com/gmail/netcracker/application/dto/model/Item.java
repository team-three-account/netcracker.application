package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Item {
    private long itemId;
    private String personId;
    private String bookerName;
    private String itemName;
    private String description;
    private String link;
    private String dueDate;
    private int priority;
    private String root;
}
