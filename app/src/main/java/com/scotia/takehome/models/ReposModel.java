package com.scotia.takehome.models;

public class ReposModel {

    private String name;
    private String description;
    private Integer stargazers_count;
    private Integer forks;

    public ReposModel(String name, String description, Integer stargazers_count, Integer forks) {
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.forks = forks;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }
}
