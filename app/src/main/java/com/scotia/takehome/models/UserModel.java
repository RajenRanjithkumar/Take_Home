package com.scotia.takehome.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {

    //Github user Model Class

    private String name;
    private String avatar_url;


    //Constructor
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


    protected UserModel(Parcel in) {
        name = in.readString();
        avatar_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(avatar_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };


}
