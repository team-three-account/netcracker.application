package com.gmail.netcracker.application.dto.model;

public class Folder {
    private String folder_id;
    private String name;
    private String creator;

    public Folder() {
    }

    public Folder(String folder_id, String name, String creator) {
        this.folder_id = folder_id;
        this.name = name;
        this.creator = creator;
    }


    public String getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folder_id=" + folder_id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
