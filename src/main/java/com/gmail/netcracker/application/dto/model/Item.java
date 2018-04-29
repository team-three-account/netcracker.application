package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Item {
    private Long itemId;
    private Long personId;
    private String bookerName;
    private String itemName;
    private String description;
    private String link;
    private String dueDate;
    private int priority;
    private Long root;
}
