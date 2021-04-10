package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class DonationUpdetes {

    private ArrayList<donationUpdate>donationUpdate;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationUpdate> getDonationUpdate() {
        return donationUpdate;
    }

    public void setDonationUpdate(ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationUpdate> donationUpdate) {
        this.donationUpdate = donationUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
