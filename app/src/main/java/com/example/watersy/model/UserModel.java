package com.example.watersy.model;

import java.io.Serializable;
import java.util.UUID;

public class UserModel implements Serializable {
    public String id;
    public String title;
    public String fName;
    public String lName;

    public UserModel(String id, String title, String fName, String lName, String province, String phone, String email) {
        this.id = id;
        this.title = title;
        this.fName = fName;
        this.lName = lName;
        this.province = province;
        this.phone = phone;
        this.email = email;
    }

    public UserModel() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    public String province;
    public String phone;
    public String email;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", province='" + province + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
