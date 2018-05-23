package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ItemTag {
    private String item;
    private String tag;

    public ItemTag() {
    }

    public ItemTag(String item, String tag) {
        this.item = item;
        this.tag = tag;
    }
}
