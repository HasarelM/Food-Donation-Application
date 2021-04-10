package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class donationDrop {

    private String drop_time;
    private int driver_id;
    private int id;
    private int state;
    private String drop_date;
    private ArrayList<images> images;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrop_time() {
        return drop_time;
    }

    public void setDrop_time(String drop_time) {
        this.drop_time = drop_time;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDrop_date() {
        return drop_date;
    }

    public void setDrop_date(String drop_date) {
        this.drop_date = drop_date;
    }

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.images> getImages() {
        return images;
    }

    public void setImages(ArrayList<com.dev.hasarelm.wastefooddonation.Model.images> images) {
        this.images = images;
    }


}
