package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class DistrictsModel {

    private ArrayList<districts> districts;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.districts> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<com.dev.hasarelm.wastefooddonation.Model.districts> districts) {
        this.districts = districts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
