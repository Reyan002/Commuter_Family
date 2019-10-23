package com.example.commuterfamily.Classes;

public class User {
    private String f_name;
    private String l_name;
    private String email;
    private String password;
    private String mobile_number;
    private String CNIC;
    private String gender;
    private enum type{DRIVE,CUSTOMER};

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getGender() {
        return gender;
    }

}
