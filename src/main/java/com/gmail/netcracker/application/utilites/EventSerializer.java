package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@PropertySource("classpath:application.properties")
public class EventSerializer implements JsonSerializer<Event> {

    @Value("${heroku.host}")
    String URL;

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
        result.addProperty("color", getColorFromPriority(src.getPriorityId()));
        result.addProperty("url", "/account/eventList/event-"+src.getEventId());
        return result;
    }

    private static String getColorFromPriority(Integer priority){
        if(priority == 1) return "red";
        if(priority == 2) return "yellow";
        if(priority == 3) return "green";
        return "grey";
    }
}
