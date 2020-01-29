package com.example.commuterfamily.Classes;

public class User {

    private String Image;
    private String Date;
    private String Time;
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private String CNIC;
    private String Gender;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getGender() {
        return Gender;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

}