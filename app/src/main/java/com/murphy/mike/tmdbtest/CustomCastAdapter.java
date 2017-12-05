package com.murphy.mike.tmdbtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomCastAdapter extends RecyclerView.Adapter<CustomCastAdapter.ViewHolder> {
    private List<GetMovieCast.CastBean> listOfCast;

    public CustomCastAdapter(List<GetMovieCast.CastBean> listOfCast){
        this.listOfCast = listOfCast;
    }

    @Override
    public CustomCastAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilecard, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomCastAdapter.ViewHolder viewHolder, int i){
        String url;
        if(listOfCast.get(i).getProfile_path() == null){
            //do nothing... default profile pic already set
        }else {
            url = "https://image.tmdb.org/t/p/w185/" + listOfCast.get(i).getProfile_path();
            Picasso.with(viewHolder.profileImg.getContext()).load(url).into(viewHolder.profileImg);
        }

       // viewHolder.character.setText(listOfCast.get(i).getCharacter());
        viewHolder.actor.setText(listOfCast.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return listOfCast.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView character, actor;
        private ImageView profileImg;
        private Context context;
        private Intent intent;

        public ViewHolder(View view){
            super(view);
            context = view.getContext();

            //character = (TextView)view.findViewById(R.id.character);
            actor = (TextView)view.findViewById(R.id.actor);
            profileImg = (ImageView)view.findViewById(R.id.profile);



        }
    }

    public GetMovieCast.CastBean getObject(int pos){
        listOfCast.get(pos);
        GetMovieCast.CastBean movieObject = listOfCast.get(pos);
        return movieObject;
    }

}
