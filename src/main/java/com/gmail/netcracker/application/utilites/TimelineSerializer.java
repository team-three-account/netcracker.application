package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@PropertySource("classpath:application.properties")
public class TimelineSerializer implements JsonSerializer<Event> {
    @Autowired
    EventService eventService;

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("title", "Busy");
        result.addProperty("start", src.getDateStart());
        result.addProperty("end", src.getDateEnd());
        return result;
    }
}
