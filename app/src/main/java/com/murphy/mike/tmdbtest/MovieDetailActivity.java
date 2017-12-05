package com.murphy.mike.tmdbtest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends Activity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static String CATEGORY = "credits";
    public int MOVIEID;
    public static String API_KEY = "232f72a9ddf4da165eb64d7ac7168102";

    private RecyclerView recyclerView;
    private CustomCastAdapter adapter;
    private List<GetMovieCast.CastBean> listOfCast;
    private GetMovieCast results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        initDetail();
        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadSearchResults();
    }

    private void loadSearchResults(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<GetMovieCast> call = myInterface.getMovieCast(MOVIEID,CATEGORY,API_KEY);

        call.enqueue(new Callback<GetMovieCast>() {
            @Override
            public void onResponse(Call<GetMovieCast> call, Response<GetMovieCast> response) {
                results = response.body();
                listOfCast = results.getCast();
                adapter = new CustomCastAdapter(listOfCast);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetMovieCast> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        RatingBar rbar = (RatingBar)findViewById(R.id.ratingBar);

        Intent i = getIntent();
        SearchMovieResults.ResultsBean object = (SearchMovieResults.ResultsBean) i.getSerializableExtra("movieObj");
        MOVIEID = object.getId();

        title.setText(object.getTitle());
        date.setText(object.getRelease_date());
        rating.setText(Double.toString(object.getVote_average()) + "/ 10.0");
        description.setText(object.getOverview());

        rbar.setRating((float) object.getVote_average());

        String url;
        if(object.getBackdrop_path() == null){
            url = "http://image.tmdb.org/t/p/w780/"+ object.getPoster_path();
        }else {
            url = "https://image.tmdb.org/t/p/w780/" + object.getBackdrop_path();
        }

        Picasso.with(backdrop.getContext()).load(url).into(backdrop);
    }

    private void popular(){
        TextView title = (TextView)findViewById(R.id.MovieTitle);
        TextView date = (TextView)findViewById(R.id.date);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView backdrop = (ImageView)findViewById(R.id.movieImage);
        RatingBar rbar = (RatingBar)findViewById(R.id.ratingBar);

        Intent i = getIntent();
        PopularMovieResults.ResultsBean object = (PopularMovieResults.ResultsBean) i.getSerializableExtra("movieObj");
        String url = "https://image.tmdb.org/t/p/w780/"+ object.getBackdrop_path();

        MOVIEID = object.getId();

        title.setText(object.getTitle());
        date.setText(object.getRelease_date());
        rating.setText(Double.toString(object.getVote_average()) + "/ 10.0");
        description.setText(object.getOverview());

        rbar.setRating((float) object.getVote_average());
        Picasso.with(backdrop.getContext()).load(url).into(backdrop);
    }

    private void initBackButton(){
        Button backButton = (Button)findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }
}
