package com.scotia.takehome.response;

import com.scotia.takehome.models.ReposModel;

import java.util.List;

public class ReposResponse {




//    @SerializedName("")
//    @Expose()
    private List<ReposModel> repos;


    public List<ReposModel> getRepos() {
        return repos;
    }

    @Override
    public String toString() {
        return "ReposResponse{" +
                "repos=" + repos +
                '}';
    }

    /*
    * //@SerializedName("")
    @Expose()
    private ReposModel repos;


    public ReposModel getRepos() {
        return repos;
    }

    @Override
    public String toString() {
        return "ReposResponse{" +
                "repos=" + repos +
                '}';
    *
    * */







}
