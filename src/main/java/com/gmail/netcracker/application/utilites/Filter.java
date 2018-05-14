package com.gmail.netcracker.application.utilites;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Filter {
    List<Long> priorities;
    List<Integer> eventTypes;
}
