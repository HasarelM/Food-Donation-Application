package com.dev.hasarelm.wastefooddonation.Model;

public class donations {

    private int state;
    private String created_at;
    private String donation_type;
    private String weight;
    private String phone;
    private String add_line_1;
    private String add_line_2;
    private String add_line_3;
    private String vehicle_type;
    private double latitude;
    private double longitude;
    private int id;
    private String donator_first_name;

    public String getDonator_first_name() {
        return donator_first_name;
    }

    public void setDonator_first_name(String donator_first_name) {
        this.donator_first_name = donator_first_name;
    }

    public String getDonator_last_name() {
        return donator_last_name;
    }

    public void setDonator_last_name(String donator_last_name) {
        this.donator_last_name = donator_last_name;
    }

    private String donator_last_name;


    public int getDonation_type_id() {
        return donation_type_id;
    }

    public void setDonation_type_id(int donation_type_id) {
        this.donation_type_id = donation_type_id;
    }

    private int donation_type_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd_line_1() {
        return add_line_1;
    }

    public void setAdd_line_1(String add_line_1) {
        this.add_line_1 = add_line_1;
    }

    public String getAdd_line_2() {
        return add_line_2;
    }

    public void setAdd_line_2(String add_line_2) {
        this.add_line_2 = add_line_2;
    }

    public String getAdd_line_3() {
        return add_line_3;
    }

    public void setAdd_line_3(String add_line_3) {
        this.add_line_3 = add_line_3;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDonation_type() {
        return donation_type;
    }

    public void setDonation_type(String donation_type) {
        this.donation_type = donation_type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }


}
