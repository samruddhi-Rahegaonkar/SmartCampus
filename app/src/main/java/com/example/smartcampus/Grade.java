package com.example.smartcampus;

public class Grade {
    private String studentName;
    private String assignmentTitle;
    private String assignmentLink;
    private String year;
    private String grade;

    // Empty constructor for Firestore
    public Grade() {}

    public Grade(String studentName, String assignmentTitle, String assignmentLink, String year, String grade) {
        this.studentName = studentName;
        this.assignmentTitle = assignmentTitle;
        this.assignmentLink = assignmentLink;
        this.year = year;
        this.grade = grade;
    }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }

    public String getAssignmentLink() { return assignmentLink; }
    public void setAssignmentLink(String assignmentLink) { this.assignmentLink = assignmentLink; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
