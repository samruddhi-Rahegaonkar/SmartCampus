package com.example.smartcampus;

public class Attendance {
    private String studentName;
    private String attendanceStatus;

    public Attendance(String studentName, String attendanceStatus) {
        this.studentName = studentName;
        this.attendanceStatus = attendanceStatus;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
