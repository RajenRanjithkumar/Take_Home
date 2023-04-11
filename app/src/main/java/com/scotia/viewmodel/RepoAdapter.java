package com.scotia.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scotia.takehome.R;
import com.scotia.takehome.models.ReposModel;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoViewHolder> {

    private Context mContext;
    private List<ReposModel> reposList;

    public RepoAdapter(Context mContext, List<ReposModel> reposList) {
        this.mContext = mContext;
        this.reposList = reposList;
    }



    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new RepoViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {

        ReposModel repo = reposList.get(position);

        if (repo.getName() == null){
            holder.repoDescription.setText("Repository Name Not Available");

        }else {
            holder.repoName.setText(repo.getName());

        }



        if (repo.getDescription() == null){
            holder.repoDescription.setText("Description Not Available");
        }else {
            holder.repoDescription.setText(repo.getDescription());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //custom dialog
                Dialog dialog = new Dialog(view.getRootView().getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView closeBt =dialog.findViewById(R.id.closeBt);

                TextView lastUpted = dialog.findViewById(R.id.updatedTime);
                TextView starCount = dialog.findViewById(R.id.starsCount);
                TextView forkCount = dialog.findViewById(R.id.forksCount);

                lastUpted.setText(repo.getUpdated_at());
                starCount.setText(repo.getStargazers_count().toString());
                forkCount.setText(repo.getForks().toString());

                closeBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

                dialog.show();




            }
        });


        //change
        //((RepoViewHolder)holder).repoName.setText(reposList.get(position).getName());

        //((RepoViewHolder)holder).repoDescription.setText(reposList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {

        return reposList.size();
    }







}
