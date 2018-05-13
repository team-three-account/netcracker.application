package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Tag {
    private Long tagId;
    private String name;


    public boolean equals(Tag tag){
        return name.equals(tag.name);
    }
}
