package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class UserDetails {

    private ArrayList<profile>profile;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.profile> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<com.dev.hasarelm.wastefooddonation.Model.profile> profile) {
        this.profile = profile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
