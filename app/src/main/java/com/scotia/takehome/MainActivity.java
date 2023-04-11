package com.scotia.takehome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;


import com.scotia.takehome.models.UserModel;
import com.scotia.takehome.models.ReposModel;
import com.scotia.takehome.request.Service;

import com.scotia.takehome.response.ReposResponse;
import com.scotia.takehome.utils.GithubApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText searchUser;
    Button searchBt;

    ImageView userImage;
    TextView userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBt = findViewById(R.id.userSearchBt);
        userName = findViewById(R.id.userId);
        searchUser = findViewById(R.id.searchUserId);
        userImage = findViewById(R.id.userImage);


        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searchUser.getText().toString().isEmpty()){

                    Toast.makeText(MainActivity.this, "Enter a username", Toast.LENGTH_SHORT).show();
                }else {

                    //Toast.makeText(MainActivity.this, searchUser.getText(), Toast.LENGTH_SHORT).show();
                    //GetRetrofitResponse(searchUser.getText().toString());
                    GetRetrofitReposResponse(searchUser.getText().toString());



                }




            }
        });
    }



    private void GetRetrofitReposResponse(String username) {


        GithubApi githubApi = Service.getUser();

        Call<List<ReposModel>> reposResponseCall = githubApi.getRepos(username);

        reposResponseCall.enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {

                if (response.isSuccessful()){

                    List<ReposModel> reposList = response.body();

                    for(ReposModel repo: reposList){

                        // handle null

                        if (repo.getDescription().equals("null")){
                            Log.v("Tag", "Not available");
                        }else{
                            Log.v("Tag", repo.getDescription());

                        }



                    }

                    /*
                    for(int i =0; i<=5;i++){
                        ReposModel testData = reposList.get(i);
                        name = testData.getName();
                        Toast.makeText(MainActivity.this, "Hello"+name, Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "Hello"+name, Toast.LENGTH_SHORT).show(); */

                }else {

                    Toast.makeText(MainActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<ReposModel>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

        //two

//            reposResponseCall.enqueue(new Callback<ReposModel>() {
//                @Override
//                public void onResponse(Call<ReposModel> call, Response<ReposModel> response) {
//
//                    if (response.code() == 200){
//
//                         //response.body().;
//                        ReposModel result= response.body().getRepos();
//
//                            List<testModel>  testData = result.getData_list();
//                            for(int i =0; i<=5; i++){
//                                testModel name = testData.get(i);
//                            }
//
//                            //Toast.makeText(MainActivity.this, repo.getName(), Toast.LENGTH_SHORT).show();
//
//                            //break;
//
//                    }else
//                    {
//                        Toast.makeText(MainActivity.this, "Not 200"+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                        userName.setText(response.errorBody().toString());
//                    }
//
//
//
//                }
//
//                @Override
//                public void onFailure(Call<ReposResponse> call, Throwable t) {
//
//                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    userName.setText(t.getMessage());
//
//                }
//            });



    }

    private void GetRetrofitResponse(String username) {

        GithubApi githubApi = Service.getUser();

        Call<UserModel> responseCall = githubApi.getUser(username);

        responseCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                UserModel user = response.body();

                if(user.getName()!= null){
                    Toast.makeText(MainActivity.this, "o/p"+user.getName(), Toast.LENGTH_SHORT).show();
                    userName.setText(user.getName());
                    Glide.with(getApplicationContext()).load(user.getAvatar_url()).into(userImage);

                }else {

                    Toast.makeText(MainActivity.this, "o/p"+user.getName(), Toast.LENGTH_SHORT).show();
                    userName.setText("user name not availabe");
                    Glide.with(getApplicationContext()).load(user.getAvatar_url()).into(userImage);

                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        });








    }
}