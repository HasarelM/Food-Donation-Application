package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class WeightRangeModel {

    private ArrayList<weights>weights;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.weights> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<com.dev.hasarelm.wastefooddonation.Model.weights> weights) {
        this.weights = weights;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
