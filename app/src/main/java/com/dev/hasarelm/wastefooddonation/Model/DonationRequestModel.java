package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class DonationRequestModel {

    private ArrayList<donationCreate>donationCreate;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationCreate> getDonationCreate() {
        return donationCreate;
    }

    public void setDonationCreate(ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationCreate> donationCreate) {
        this.donationCreate = donationCreate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
