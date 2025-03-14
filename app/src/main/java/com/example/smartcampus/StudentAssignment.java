package com.example.smartcampus;

public class StudentAssignment {
    private String id; // Firestore Document ID
    private String name;
    private String year;
    private String link;
    private String grade; // New field for grades

    // Empty constructor for Firestore
    public StudentAssignment() {}

    public StudentAssignment(String id, String name, String year, String link, String grade) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.link = link;
        this.grade = grade;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
