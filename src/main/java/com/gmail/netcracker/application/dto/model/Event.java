package com.gmail.netcracker.application.dto.model;

import lombok.Data;

@Data
public class Event {
    private int eventId;
    private String name;
    private String description;
    private Long creator;
    private String dateStart;
    private String dateEnd;
    private String placeId;
    private String placeAddress;
    private String periodicity;
    private String type;
    private Boolean draft;
    private Integer folder;
    private Integer typeId;
    private Double width;
    private Double longitude;
    private String eventPlaceName;
    private String photo;
    private String priority;

    public Boolean isDraft() {
        return draft;
    }

    public void setDraft(Boolean isDraft) {
        this.draft = isDraft;
    }
}
