package com.example.asus.mediu;

/**
 * Created by asus on 2/17/2018.
 */

public class UserSingle {

    public String firstname;
    public String image;
    public String lastname;
    public String thumbnail;



    public UserSingle(){

    }

    public UserSingle(String firstname, String image, String lastname, String thumbnail) {
        this.firstname = firstname;
        this.image = image;
        this.lastname = lastname;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return firstname;
    }

    public void setName(String firstname) {
        this.firstname = firstname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getThumb_image() {
        return thumbnail;
    }

    public void setThumb_image(String thumb_image) {
        this.thumbnail = thumbnail;
    }

}