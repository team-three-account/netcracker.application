package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
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
    private Long event;
    private String image;
    private Set<Tag> tags;
    private Long likes;
    private Integer isLiked;
}
