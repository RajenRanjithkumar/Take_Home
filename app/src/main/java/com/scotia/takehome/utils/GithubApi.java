package com.scotia.takehome.utils;

import com.scotia.takehome.models.UserModel;
import com.scotia.takehome.models.ReposModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    // GET request to fetch user name and user avatar
    @GET("{user_name}?")
    Call<UserModel> getUser(
            @Path("user_name") String user_name
    );


    // GET request to fetch a user's repositories
    @GET("{user_name}/repos")
    Call<List<ReposModel>> getRepos(
            @Path("user_name") String user_name);





}
