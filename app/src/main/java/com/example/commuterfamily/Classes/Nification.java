package com.example.commuterfamily.Classes;

public class Nification {
    private String from ;
    private String type;


    public Nification() {
    }


    public Nification(String from, String type) {
        this.from = from;
        this.type = type;
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

