package com.example.smartcampus;

public class Assignment {
    private String id;  // ✅ Firestore Document ID
    private String title;
    private String description;
    private String deadline;

    // Empty Constructor (Required for Firestore)
    public Assignment() {}

    public Assignment(String id, String title, String description, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {  // ✅ Setter for ID
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
