package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class RiderRegisterModel {

    private ArrayList<register> register;
    private String message;

    public ArrayList<register> getRegister() {
        return register;
    }

    public void setRegister(ArrayList<register> register) {
        this.register = register;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
