package com.dev.hasarelm.wastefooddonation.Model;

import java.util.ArrayList;

public class LoginUserModel {

    private ArrayList<login>login;

    public ArrayList<com.dev.hasarelm.wastefooddonation.Model.login> getLogin() {
        return login;
    }

    public void setLogin(ArrayList<com.dev.hasarelm.wastefooddonation.Model.login> login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
