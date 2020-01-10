package com.example.commuterfamily.Classes;

public class Nification {
    private String from ;
    private String type;
    private String Date;
    private String Time;


    public Nification() {
    }


    public Nification(String from, String type, String date, String time) {
        this.from = from;
        this.type = type;
        Date = date;
        Time = time;
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

