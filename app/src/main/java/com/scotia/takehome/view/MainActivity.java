package com.scotia.takehome.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.view.inputmethod.InputMethodManager;
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


    LinearLayout homeLayout;
    TextInputEditText searchUser;
    Button searchBt;
    ImageView userImage;
    TextView userName;
    Boolean check;


    private RecyclerView repoRecyclerView;
    private RepoAdapter repoAdapter;
    private List<ReposModel> repositoryList;
    private LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking all the layout fields

        homeLayout = findViewById(R.id.homeLinearLayout);
        searchBt = findViewById(R.id.userSearchBt);
        userName = findViewById(R.id.userId);
        searchUser = findViewById(R.id.searchUserId);
        userImage = findViewById(R.id.userImage);


        // Setting up recycler view for the user's repository list
        repoRecyclerView = findViewById(R.id.reposRV);
        repoRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        repoRecyclerView.setLayoutManager(linearLayoutManager);
        repositoryList = new ArrayList<>();
        repoAdapter = new RepoAdapter(getApplicationContext(), repositoryList);
        repoRecyclerView.setAdapter(repoAdapter);


        // On click the search button, data gets fetched from the GitHub Api
        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checks the internet connection
                check = checkInternetConnection(view);

                if(check){

                    // Get the username from the user
                    String username = searchUser.getText().toString().trim();

                    //to hide the keyboard after button click
                    hideKeyboard();

                    // Validate the username
                    ValidateUsername(username);

                }

            }
        });


    }


    private boolean checkInternetConnection(View view){

        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null &&  nInfo.isConnected();

        //Display a custom dialog when user goes offline
        Dialog dialog = new Dialog(view.getRootView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_detect_internet);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button refresh = dialog.findViewById(R.id.refresh_Bt);

        if (!connected){

            dialog.show();

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkInternetConnection(view);
                    dialog.dismiss();

                }
            });

            return false;


        }else {

            dialog.dismiss();
            return true;

        }


    }

    private void hideKeyboard() {

        // Get the view currently in focus
        View view = this.getCurrentFocus();

        if (view != null) {
            //will hide the the keyboard
            InputMethodManager manager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void ValidateUsername(String username) {

        // username name cannot have just the special characters
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!]");

        if (username.isEmpty()){

            Toast.makeText(MainActivity.this, "Enter a username", Toast.LENGTH_SHORT).show();

        } else if (regex.matcher(username).find()) {

            Toast.makeText(MainActivity.this, "Invalid Username ", Toast.LENGTH_SHORT).show();

        } else {

            // Will get the User name and Avatar from the Github Api
            GetRetrofitResponse(username);

            // Will get the user's repositories from the Github Api
            GetRetrofitReposResponse(username);

        }


    }


    private void GetRetrofitResponse(String username) {

        //send a Get Request to get the username and avatar
        GithubApi githubApi = Service.getUser();
        Call<UserModel> responseCall = githubApi.getUser(username);

        responseCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                if(response.isSuccessful()){

                    //Update UI
                    UserModel user = response.body();

                    if(user.getName()!= null){

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



    private void GetRetrofitReposResponse(String username) {

        //send a Get Request to get the user's repository
        GithubApi githubApi = Service.getUser();
        Call<List<ReposModel>> reposResponseCall = githubApi.getRepos(username);

        reposResponseCall.enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {

                //Update UI
                if (response.isSuccessful()){

                    List<ReposModel> reposList = response.body();
                    repositoryList.clear();

                    if(!reposList.isEmpty()){

                        repositoryList.addAll(reposList);

                        repoAdapter.notifyDataSetChanged();

                    }else {

                        Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        //When user has no repositories empty the List
                        repositoryList.clear();
                        repoAdapter.notifyDataSetChanged();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}