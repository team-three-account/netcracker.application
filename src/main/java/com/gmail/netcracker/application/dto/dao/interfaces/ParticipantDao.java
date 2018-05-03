package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.Priority;

import java.util.List;

public interface ParticipantDao {

    void setPriority(Integer priority, int event_id, Long user_id);

    List<Participant> getPriorityForUserEvents(Long user_id);

    Participant getParticipant(int event_id, Long user_id);

}
