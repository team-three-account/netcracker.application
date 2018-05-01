package com.gmail.netcracker.application.dto.model;

public class Pereodicity {
    private String per_id;
    private String frequency;
    private int value;

    public Pereodicity() {
    }

    public Pereodicity(String per_id, String frequency, int value) {
        this.per_id = per_id;
        this.frequency = frequency;
        this.value = value;
    }


    public String getPer_id() {
        return per_id;
    }

    public void setPer_id(String per_id) {
        this.per_id = per_id;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pereodicity{" +
                "per_id=" + per_id +
                ", frequency='" + frequency + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}