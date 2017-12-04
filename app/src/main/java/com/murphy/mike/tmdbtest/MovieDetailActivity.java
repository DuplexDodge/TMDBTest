package com.murphy.mike.tmdbtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initDetail();
    }

    private void initDetail() {

        String type = getIntent().getStringExtra("type");
        if(type.equals("popular")) {
            popular();
        }else if(type.equals("search")){
            search();
        }

        initBackButton();
    }

    private void search(){
        TextView title = (TextView)findViewById(R.id.MovieTitle);
        TextView date = (TextView)findViewById(R.id.date);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView backdrop = (ImageView)findViewById(R.id.movieImage);

        Intent i = getIntent();
        SearchMovieResults.ResultsBean object = (SearchMovieResults.ResultsBean) i.getSerializableExtra("movieObj");
        String url = "https://image.tmdb.org/t/p/w780/"+ object.getBackdrop_path();

        title.setText(object.getTitle());
        date.setText(object.getRelease_date());
        rating.setText(Double.toString(object.getVote_average()));
        description.setText(object.getOverview());
        Picasso.with(backdrop.getContext()).load(url).into(backdrop);
    }

    private void popular(){
        TextView title = (TextView)findViewById(R.id.MovieTitle);
        TextView date = (TextView)findViewById(R.id.date);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView backdrop = (ImageView)findViewById(R.id.movieImage);

        Intent i = getIntent();
        PopularMovieResults.ResultsBean object = (PopularMovieResults.ResultsBean) i.getSerializableExtra("movieObj");
        String url = "https://image.tmdb.org/t/p/w780/"+ object.getBackdrop_path();

        title.setText(object.getTitle());
        date.setText(object.getRelease_date());
        rating.setText(Double.toString(object.getVote_average()));
        description.setText(object.getOverview());
        Picasso.with(backdrop.getContext()).load(url).into(backdrop);
    }

    private void initBackButton(){
        Button backButton = (Button)findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
