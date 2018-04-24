package com.gmail.netcracker.application.dto.model;

public class Item {
    private String itemId;
    private String personId;
    private String bookerName;
    private String itemName;
    private String description;
    private String link;
    private String dueDate;
    private int priority;
    private String root;

    public Item() {
    }

    public Item(String itemId, String personId, String bookerName, String itemName, String description, String link, String dueDate, int priority, String root) {
        this.itemId = itemId;
        this.personId = personId;
        this.bookerName = bookerName;
        this.itemName = itemName;
        this.description = description;
        this.link = link;
        this.dueDate = dueDate;
        this.priority = priority;
        this.root = root;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (priority != item.priority) return false;
        if (!itemId.equals(item.itemId)) return false;
        if (!personId.equals(item.personId)) return false;
        if (!bookerName.equals(item.bookerName)) return false;
        if (itemName != null ? !itemName.equals(item.itemName) : item.itemName != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (link != null ? !link.equals(item.link) : item.link != null) return false;
        if (dueDate != null ? !dueDate.equals(item.dueDate) : item.dueDate != null) return false;
        return root != null ? root.equals(item.root) : item.root == null;
    }

    @Override
    public int hashCode() {
        int result = itemId.hashCode();
        result = 31 * result + personId.hashCode();
        result = 31 * result + bookerName.hashCode();
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", personId='" + personId + '\'' +
                ", bookerName='" + bookerName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", priority=" + priority +
                ", root='" + root + '\'' +
                '}';
    }
}
