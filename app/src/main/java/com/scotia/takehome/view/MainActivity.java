package com.scotia.takehome.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;


import com.scotia.takehome.R;
import com.scotia.takehome.viewmodel.RepoAdapter;
import com.scotia.takehome.models.UserModel;
import com.scotia.takehome.models.ReposModel;
import com.scotia.takehome.request.Service;

import com.scotia.takehome.utils.GithubApi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    CardView internetOfflinePopUp;
    LinearLayout homeLayout;
    TextInputEditText searchUser;
    Button searchBt, refreshBt;
    ImageView userImage;
    TextView userName;

    private RecyclerView repoRecyclerView;
    private RepoAdapter repoAdapter;
    private List<ReposModel> repositoryList;
    private LinearLayoutManager linearLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking all the layout fields

        internetOfflinePopUp = findViewById(R.id.internetPopup);
        homeLayout = findViewById(R.id.homeLinearLayout);
        searchBt = findViewById(R.id.userSearchBt);
        refreshBt = findViewById(R.id.refreshBt);
        userName = findViewById(R.id.userId);
        searchUser = findViewById(R.id.searchUserId);
        userImage = findViewById(R.id.userImage);

        repoRecyclerView = findViewById(R.id.reposRV);
        repoRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        repoRecyclerView.setLayoutManager(linearLayoutManager);
        repositoryList = new ArrayList<>();
        repoAdapter = new RepoAdapter(getApplicationContext(), repositoryList);
        repoRecyclerView.setAdapter(repoAdapter);

        checkInternetConnection();



        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkInternetConnection();
                String username = searchUser.getText().toString().trim();
                ValidateUsername(username);



            }
        });






    }


    private void checkInternetConnection(){

        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null &&  nInfo.isConnected();


        if (connected){

            internetOfflinePopUp.setVisibility(View.GONE);
            homeLayout.setVisibility(View.VISIBLE);

        }else {

            internetOfflinePopUp.setVisibility(View.VISIBLE);
            homeLayout.setVisibility(View.GONE);
            refreshBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     checkInternetConnection();

                }
            });

        }



    }

    private void ValidateUsername(String username) {

        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        if (username.isEmpty()){

            Toast.makeText(MainActivity.this, "Enter a username", Toast.LENGTH_SHORT).show();

        } else if (regex.matcher(username).find()) {

            Toast.makeText(MainActivity.this, "Invalid Username ", Toast.LENGTH_SHORT).show();

        } else {

            // will get the users information
            GetRetrofitResponse(username);

            // will get the users repositories
            GetRetrofitReposResponse(username);


        }


    }






    private void GetRetrofitReposResponse(String username) {


        GithubApi githubApi = Service.getUser();

        Call<List<ReposModel>> reposResponseCall = githubApi.getRepos(username);

        reposResponseCall.enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {

                if (response.isSuccessful()){

                    List<ReposModel> reposList = response.body();
                    repositoryList.clear();

                    if(!reposList.isEmpty()){

                        for(ReposModel repo: reposList){

                            repositoryList.add(repo);

                            // handle null

//                        if (repo.getDescription().equals("null")){
//                            Log.v("Tag", "Not available");
//                        }else{
//                            Log.v("Tag", repo.getDescription());
//
//                        }
                        }

                        repoAdapter.notifyDataSetChanged();


                    }else {

                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    Toast.makeText(MainActivity.this, "No response from the server", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<ReposModel>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Server Down, Try again later!!", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void GetRetrofitResponse(String username) {

        GithubApi githubApi = Service.getUser();

        Call<UserModel> responseCall = githubApi.getUser(username);

        responseCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                if(response.isSuccessful()){

                    UserModel user = response.body();

                    if(user.getName()!= null){
                        //Toast.makeText(MainActivity.this, "o/p"+user.getName(), Toast.LENGTH_SHORT).show();
                        userName.setText(user.getName());
                        Glide.with(getApplicationContext())
                                .load(user.getAvatar_url())
                                .placeholder(R.drawable.github_default)
                                .into(userImage);

                    }else {


                        userName.setText(R.string.user_not_found);
                        Glide.with(getApplicationContext())
                                .load(user.getAvatar_url())
                                .placeholder(R.drawable.github_default)
                                .into(userImage);

                    }

                }else {
                    Toast.makeText(MainActivity.this, "Server Down!!", Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Server Down!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}