package com.scotia.takehome.request;

import com.scotia.takehome.utils.GithubApi;
import com.scotia.takehome.utils.Routes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Routes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static GithubApi githubApi = retrofit.create(GithubApi.class);

    public static GithubApi getUser()
    {
        return githubApi;
    }


}
