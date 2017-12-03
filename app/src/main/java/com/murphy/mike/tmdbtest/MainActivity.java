package com.murphy.mike.tmdbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "232f72a9ddf4da165eb64d7ac7168102";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

    private TextView movieTitleTextView;

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<PopularMovieResults.ResultsBean> listOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadPopularMovies();
    }
    private void loadPopularMovies(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<PopularMovieResults> call = myInterface.getPopularMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<PopularMovieResults>() {
            @Override
            public void onResponse(Call<PopularMovieResults> call, Response<PopularMovieResults> response) {
                PopularMovieResults results = response.body();
                //List<PopularMovieResults.ResultsBean> listOfMovies = results.getResults();
                //
                listOfMovies = results.getResults();
                adapter = new CustomAdapter(listOfMovies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PopularMovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
