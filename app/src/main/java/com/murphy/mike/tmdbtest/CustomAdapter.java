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

import java.util.List;

import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

/**
 * Created by Mike on 12/2/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<PopularMovieResults.ResultsBean> listOfMovies;

    public CustomAdapter(List<PopularMovieResults.ResultsBean> listOfMovies){
        this.listOfMovies = listOfMovies;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, int i){
        String url = "https://image.tmdb.org/t/p/w780/"+ listOfMovies.get(i).getBackdrop_path();

        if(listOfMovies.get(i).getBackdrop_path() == null){
            if(listOfMovies.get(i).getPoster_path() == null){
                viewHolder.backdrop.setImageResource(R.drawable.tmdblack);
            }else {
                url = "http://image.tmdb.org/t/p/w780/" + listOfMovies.get(i).getPoster_path();
                Picasso.with(viewHolder.backdrop.getContext()).load(url).into(viewHolder.backdrop);
            }
        }else{
            url = "https://image.tmdb.org/t/p/w780/" + listOfMovies.get(i).getBackdrop_path();
            Picasso.with(viewHolder.backdrop.getContext()).load(url).into(viewHolder.backdrop);
        }

        viewHolder.title.setText(listOfMovies.get(i).getTitle());
        try {
            viewHolder.year.setText(listOfMovies.get(i).getRelease_date().toString().substring(0, 4));
        }catch(Exception e){
            //No Release Date
            viewHolder.year.setText("N/A");
        }
        Picasso.with(viewHolder.backdrop.getContext()).load(url).into(viewHolder.backdrop);
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, year;
        private ImageView backdrop;
        private Context context;
        private Intent intent;

        public ViewHolder(View view){
            super(view);
            context = view.getContext();

            title = (TextView)view.findViewById(R.id.title);
            year = (TextView)view.findViewById(R.id.year);
            backdrop = (ImageView)view.findViewById(R.id.backdropImg);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent =  new Intent(context, MovieDetailActivity.class);
                    PopularMovieResults.ResultsBean object = getObject(getAdapterPosition());
                    intent.putExtra("movieObj", object);
                    intent.putExtra("type","popular");
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            });

        }
    }

    public PopularMovieResults.ResultsBean getObject(int pos){
        listOfMovies.get(pos);
        com.murphy.mike.tmdbtest.PopularMovieResults.ResultsBean movieObject = listOfMovies.get(pos);
        return movieObject;
    }


}
