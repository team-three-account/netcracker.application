package com.gmail.netcracker.application.dto.model;

public class Type {
    private String type_id;
    private String value;

    public Type() {
    }

    public Type(String type_id, String value) {
        this.type_id = type_id;
        this.value = value;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Type{" +
                "type_id=" + type_id +
                ", value='" + value + '\'' +
                '}';
    }
}
