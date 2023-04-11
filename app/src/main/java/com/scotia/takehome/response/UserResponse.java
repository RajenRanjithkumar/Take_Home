package com.scotia.takehome.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.scotia.takehome.models.UserModel;

public class UserResponse {


    @SerializedName("")
    @Expose
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                '}';
    }
}
