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
    private String priority;
    private String type;
    private boolean draft;
    private int folder;
    private int typeId;
    private double width;
    private double longitude;
    private String eventPlaceName;
    private String photo;
}
