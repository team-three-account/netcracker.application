package com.gmail.netcracker.application.dto.model;

public class Tag {
    private String tag_id;
    private String name;

    public Tag() {
    }

    public Tag(String tag_id, String name) {
        this.tag_id = tag_id;
        this.name = name;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Tag{" +
                "tag_id=" + tag_id +
                ", name='" + name + '\'' +
                '}';
    }
}
