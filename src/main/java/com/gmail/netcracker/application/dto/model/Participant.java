package com.gmail.netcracker.application.dto.model;

public class Participant {
    private String person;
    private String event;
    private int priority;
    private boolean countdown;
    private boolean is_accepted;

    public Participant() {
    }

    public Participant(String person, String event, int priority, boolean countdown, boolean is_accepted) {
        this.person = person;
        this.event = event;
        this.priority = priority;
        this.countdown = countdown;
        this.is_accepted = is_accepted;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getCountdown() {
        return countdown;
    }

    public void setCountdown(boolean countdown) {
        this.countdown = countdown;
    }

    public boolean getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "person=" + person +
                ", event='" + event + '\'' +
                ", priority='" + priority + '\'' +
                ", countdown='" + countdown + '\'' +
                ", is_accepted='" + is_accepted + '\'' +
                '}';
    }

}
