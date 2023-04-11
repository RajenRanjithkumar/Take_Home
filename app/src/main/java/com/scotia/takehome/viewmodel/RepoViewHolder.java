package com.scotia.takehome.viewmodel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scotia.takehome.R;

public class RepoViewHolder extends RecyclerView.ViewHolder  {

    public TextView repoName, repoDescription;


    public RepoViewHolder(@NonNull View itemView) {
        super(itemView);


        repoName = itemView.findViewById(R.id.repoName);
        repoDescription = itemView.findViewById(R.id.repoDescription);



    }

}
