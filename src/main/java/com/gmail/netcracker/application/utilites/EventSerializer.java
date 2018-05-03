package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
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
        //result.addProperty("color", getColorFromPriority(eventService.getPriority(src.getEventId())));
        return result;
    }

    private static String getColorFromPriority(String priority){
        if("urgent".equals(priority)) return "red";
        if("normal".equals(priority)) return "yellow";
        if("low".equals(priority)) return "green";
        return "grey";
    }
}
