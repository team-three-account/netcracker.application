package com.gmail.netcracker.application.utilites;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Filter {
    public Filter(){
        priorities = new ArrayList<>();
        eventTypes = new ArrayList<>();
        checkedFriends = new ArrayList<>();
    }
    List<Long> priorities;
    List<Long> eventTypes;
    List<Long> checkedFriends;
}
