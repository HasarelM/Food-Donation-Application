package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class DonationTypeModel {

    private ArrayList<donationTypes>donationTypes;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationTypes> getDonationTypes() {
        return donationTypes;
    }

    public void setDonationTypes(ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationTypes> donationTypes) {
        this.donationTypes = donationTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
