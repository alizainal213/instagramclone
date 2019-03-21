package com.example.instagramclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostHolder> implements View.OnClickListener {
    private  ArrayList<HashMap<String, String>> listdata;
    private Context context;
    public AdapterPost(FragmentActivity activity, ArrayList<HashMap<String, String>> postData) {
        listdata = postData;
        context = activity;

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int i) {
        postHolder.txtUsername.setText(listdata.get(i).get("username"));
        postHolder.txtusercap.setText(listdata.get(i).get("username"));
        postHolder.txtcaption.setText(listdata.get(i).get("caption"));
        String URL_GAMBAR = "https://webalizainal.000webhostapp.com/img/";
        Glide.with(context)
                .load(URL_GAMBAR + listdata.get(i).get("gambar"))
                .into(postHolder.imgpost);

        Glide.with(context)
                .load(URL_GAMBAR + listdata.get(i).get("p_image"))
                .into(postHolder.imgphotoprofile);
        postHolder.btnLike.setOnClickListener(this);
        postHolder.btnShare.setOnClickListener(this);
        postHolder.btnMore.setOnClickListener(this);
        postHolder.btnFav.setOnClickListener(this);
        postHolder.btnComment.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "Kamu Menekan Tombol", Toast.LENGTH_SHORT).show();

    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtusercap, txtcaption;
        ImageView imgphotoprofile, imgpost, btnMore, btnLike, btnComment, btnShare, btnFav;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txt_username);
            txtusercap = itemView.findViewById(R.id.txt_usernamecap);
            txtcaption = itemView.findViewById(R.id.txt_caption);
            imgphotoprofile = itemView.findViewById(R.id.img_profile);
            imgpost = itemView.findViewById(R.id.img_post);
            btnMore = itemView.findViewById(R.id.btn_more);
            btnLike = itemView.findViewById(R.id.btn_like);
            btnComment = itemView.findViewById(R.id.btn_comment);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnFav = itemView.findViewById(R.id.btn_fav);
        }

    }
}
