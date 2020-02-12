package com.example.commuterfamily.Classes;

public class Nification {
    private String from ;
    private String type;
    private String Date;
    private String Time;
    private String NotiId;
    private String WantTo;


    public Nification() {
    }

    public String getWantTo() {
        return WantTo;
    }

    public void setWantTo(String wantTo) {
        WantTo = wantTo;
    }

    public String getNotiId() {
        return NotiId;
    }

    public void setNotiId(String notiId) {
        NotiId = notiId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public String getType() {
        return type;
    }
}

