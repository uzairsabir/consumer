package com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ProfileDT extends RealmObject {

    public ProfileDT() {

    }

    @PrimaryKey
    private int user_id = 0;

    private String username = "";
    private String first_name = "";
    private String last_name = "";
    private String email_1 = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String mobile_number_1 = "";
    private int zip_code = 0;
    private String image_url = "";
    private String is_register_user = "";


//    "body": [{
//        "id": "12",
//                "username": "0923319969696",
//                "first_name": "Uzair",
//                "last_name": "Sabir",
//                "email_1": "",
//                "address": null,
//                "city": null,
//                "state": null,
//                "country": null,
//                "mobile_number_1": "0923319969696",
//                "zip_code": "75050",
//                "image_url": null,
//                "is_register_user": "1",
//                "sub_category_id": "13",
//                "sub_category_title": "Handyman",
//                "category_id": "3",
//                "category_title": "Home Services"
//    }]


//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIs_register_user() {
        return is_register_user;
    }

    public void setIs_register_user(String is_register_user) {
        this.is_register_user = is_register_user;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getMobile_number_1() {
        return mobile_number_1;
    }

    public void setMobile_number_1(String mobile_number_1) {
        this.mobile_number_1 = mobile_number_1;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
