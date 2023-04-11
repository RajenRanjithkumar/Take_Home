package com.scotia.takehome.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReposModel  {

    private String name;
    private String description;
    private Integer stargazers_count;
    private Integer forks;

    private String updated_at;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public Integer getForks() {
        return forks;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
