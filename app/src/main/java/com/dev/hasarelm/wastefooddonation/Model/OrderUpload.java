package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class OrderUpload {

    private ArrayList<donationDrop>donationDrop;
    private String message;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationDrop> getDonationDrop() {
        return donationDrop;
    }

    public void setDonationDrop(ArrayList<com.dev.hasarelm.wastefooddonation.Model.donationDrop> donationDrop) {
        this.donationDrop = donationDrop;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
