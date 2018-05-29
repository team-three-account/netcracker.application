package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@PropertySource("classpath:application.properties")
public class TimelineSerializer implements JsonSerializer<Event> {

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("title", src.getName());
        result.addProperty("start", src.getDateStart());
        result.addProperty("end", src.getDateEnd());
        return result;
    }
}
