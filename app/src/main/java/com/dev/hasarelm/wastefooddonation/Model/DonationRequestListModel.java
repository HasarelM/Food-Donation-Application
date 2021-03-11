package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class DonationRequestListModel {

    private ArrayList<donations>donations;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.donations> getDonations() {
        return donations;
    }

    public void setDonations(ArrayList<com.dev.hasarelm.wastefooddonation.Model.donations> donations) {
        this.donations = donations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
