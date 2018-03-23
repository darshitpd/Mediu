package com.example.asus.mediu;

/**
 * Created by Dell on 3/3/2018.
 */

public class Upload {

    public String name;
    public String url;
    public String date;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url,String date) {
        this.name = name;
        this.url = url;
        this.date=date;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
