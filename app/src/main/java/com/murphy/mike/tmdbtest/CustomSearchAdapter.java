package com.murphy.mike.tmdbtest;

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

/**
 * Created by Mike on 12/3/2017.
 */

public class CustomSearchAdapter extends RecyclerView.Adapter<CustomSearchAdapter.ViewHolder> {
    private List<SearchMovieResults.ResultsBean> listOfMovies;

    public CustomSearchAdapter(List<SearchMovieResults.ResultsBean> listOfMovies){
        this.listOfMovies = listOfMovies;
    }

    @Override
    public CustomSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomSearchAdapter.ViewHolder viewHolder, int i){
        String url;
        if(listOfMovies.get(i).getBackdrop_path() == null){
            url = "http://image.tmdb.org/t/p/w780/"+ listOfMovies.get(i).getPoster_path();
        }else {
            url = "https://image.tmdb.org/t/p/w780/" + listOfMovies.get(i).getBackdrop_path();
        }

        viewHolder.title.setText(listOfMovies.get(i).getTitle());
        try {
            viewHolder.year.setText(listOfMovies.get(i).getRelease_date().toString().substring(0, 4));
        }catch(Exception e){
            //No release date
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
                    SearchMovieResults.ResultsBean object = getObject(getAdapterPosition());
                    intent.putExtra("movieObj", object);
                    intent.putExtra("type","search");
                    context.startActivity(intent);
                }
            });

        }
    }

    public SearchMovieResults.ResultsBean getObject(int pos){
        listOfMovies.get(pos);
        com.murphy.mike.tmdbtest.SearchMovieResults.ResultsBean movieObject = listOfMovies.get(pos);
        return movieObject;
    }

}
