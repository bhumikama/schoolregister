package com.example.sdvs;

public class ClassBean {

    //long cid;
    String class_name;
    String subject_name;
    long cid;

    public ClassBean(long cid,String class_name, String subject_name) {
        this.class_name = class_name;
        this.subject_name = subject_name;
        this.cid= cid;
    }

    public long getCid() {
        return cid;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public void setCid(long cid){
        this.cid = cid;
    }
}
