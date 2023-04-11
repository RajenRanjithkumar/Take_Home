package com.scotia.takehome.models;


//User repository model class
public class ReposModel  {

    //Required fields
    private String name;
    private String description;
    private Integer stargazers_count;
    private Integer forks;
    private String updated_at;

    //Constructors
    public ReposModel(String name, String description, Integer stargazers_count, Integer forks, String updated_at) {
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.forks = forks;
        this.updated_at = updated_at;
    }

    //Getters
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
