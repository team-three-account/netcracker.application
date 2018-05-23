package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Like {
    private Long itemId;
    private Long userId;
    private Boolean isLiked;
}