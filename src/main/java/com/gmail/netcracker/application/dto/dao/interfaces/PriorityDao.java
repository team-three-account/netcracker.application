package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.Priority;

import java.util.List;

public interface PriorityDao {
    List<Priority> getAllPriority();

    void setPriorityToEvent(Long priority, int eventId, Long userId);

    Participant getParticipant(int eventId, Long personId);
}
