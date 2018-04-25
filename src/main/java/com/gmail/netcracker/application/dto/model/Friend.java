package com.gmail.netcracker.application.dto.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@Setter
@Getter
public class Friend {
    private Long sender;
    private Long recipient;
    private boolean isAccepted;

    public Friend(){

    }

    public Friend(Long sender, Long recipient, boolean isAccepted){
        this.sender=sender;
        this.recipient = recipient;
        this.isAccepted=isAccepted;
    }
}
