package com.gmail.netcracker.application.dto.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@Setter
@Getter
public class Friend {
    private String sender;
    private String recipient;
    private boolean isAccepted;

    public Friend(){

    }

    public Friend(String sender, String recipient, boolean isAccepted){
        this.sender=sender;
        this.recipient = recipient;
        this.isAccepted=isAccepted;
    }
}
