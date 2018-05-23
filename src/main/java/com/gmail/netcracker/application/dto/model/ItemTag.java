package com.gmail.netcracker.application.dto.model;

import org.springframework.stereotype.Component;

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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return "ItemTag{" +
                "item=" + item +
                ", tag='" + tag + '\'' +
                '}';
    }
}
