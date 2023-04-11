package com.scotia.takehome.models;



// User Model Class
public class UserModel  {

    //Required fields
    private String name;
    private String avatar_url;


    //Constructors
    public UserModel(String name, String avatar_url) {
        this.name = name;
        this.avatar_url = avatar_url;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }





}
