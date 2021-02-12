package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class VehicleTypeModel {

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.vehicleTypes> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(ArrayList<com.dev.hasarelm.wastefooddonation.Model.vehicleTypes> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    private ArrayList<vehicleTypes> vehicleTypes;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

}
