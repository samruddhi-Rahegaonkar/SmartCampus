package com.example.smartcampus;

public class Notice {
    private String title;
    private String date;
    private String description;

    public Notice(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
}
