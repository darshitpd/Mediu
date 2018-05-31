package com.example.asus.mediu;

/**
 * Created by asus on 31-May-18.
 */

public class AptSingle {
    public String doctor_name;

    public AptSingle(){

    }
    public AptSingle(String doctor_name) {
        this.doctor_name=doctor_name;
    }

    public String getName() {
        return doctor_name;
    }

    public void setName(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
