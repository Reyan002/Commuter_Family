package com.commutersfamily.commuterfamily.Classes.IntroClasses;

public class ScreenItem {

    private String title, des;
    private int imageInt;

    public ScreenItem(String title, String des, int imageInt) {
        this.title = title;
        this.des = des;
        this.imageInt = imageInt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getImageInt() {
        return imageInt;
    }

    public void setImageInt(int imageInt) {
        this.imageInt = imageInt;
    }
}
