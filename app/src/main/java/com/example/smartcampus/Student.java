package com.example.smartcampus;

public class Student {
    private String id;
    private String name;
    private boolean isPresent;

    public Student(String id, String name, boolean isPresent) {
        this.id = id;
        this.name = name;
        this.isPresent = isPresent;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isPresent() { return isPresent; }
    public void setPresent(boolean present) { isPresent = present; }
}
