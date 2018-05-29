package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@PropertySource("classpath:application.properties")
public class EventSerializer implements JsonSerializer<Event> {

    @Autowired
    EventService eventService;

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("id", src.getEventId());
        result.addProperty("title", src.getName());
        result.addProperty("start", src.getDateStart());
        result.addProperty("end", src.getDateEnd());
        getColorFromPriority(result, src.getPriorityId());
        result.addProperty("url", "/account/eventList/event-"+src.getEventId());
        return result;
    }

    private static void getColorFromPriority(JsonObject json, Long priority){
        if (priority == 1) json.addProperty("color", "red");
        else if (priority == 2) {
            json.addProperty("color", "yellow");
            json.addProperty("textColor", "black");
        } else if (priority == 3) json.addProperty("color", "green");
        else json.addProperty("color", "grey");
    }
}
