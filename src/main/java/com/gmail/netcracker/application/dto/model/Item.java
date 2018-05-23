package com.gmail.netcracker.application.dto.model;

import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Set;

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
    private String image;
    private Set<Tag> tags;
    private int likes;
    private Integer isLiked;
}
