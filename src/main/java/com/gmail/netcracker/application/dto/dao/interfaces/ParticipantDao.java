package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.Priority;

import java.util.List;

public interface ParticipantDao {

    void setPriority(Integer priority, int eventId, Long userId);

    List<Participant> getPriorityForUserEvents(Long userId);

    Participant getParticipant(int eventId, Long userId);

}
