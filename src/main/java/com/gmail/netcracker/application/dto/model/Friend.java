package com.gmail.netcracker.application.dto.model;

import lombok.Generated;
import lombok.Setter;

@Generated
@Setter
public class Friend {
    private String sender;
    private String recipient;
    private boolean isAccepted;

    public Friend(String sender, String recipient, boolean isAccepted){
        this.sender=sender;
        this.recipient = recipient;
        this.isAccepted=isAccepted;
    }
}
