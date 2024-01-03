package com.example.sdvs;

public class TeacherBean {

    public int getStudent_roll() {
        return student_roll;
    }

    public void setStudent_roll(int student_roll) {
        this.student_roll = student_roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TeacherBean(long sid,int student_roll, String name) {
        this.student_roll = student_roll;
        this.name = name;
        this.sid = sid;
        status=" ";
    }

    private int student_roll;
    private String name;

    public TeacherBean(long sid) {
        this.sid = sid;
    }

    private String status;
    private long sid;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
