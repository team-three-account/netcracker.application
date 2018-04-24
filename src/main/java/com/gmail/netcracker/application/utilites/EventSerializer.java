package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EventSerializer implements JsonSerializer<Event> {

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("id", src.getEventId());
        result.addProperty("title", src.getName());
        result.addProperty("start", src.getDateStart());
        result.addProperty("end", src.getDateEnd());
        return null;
    }
}
