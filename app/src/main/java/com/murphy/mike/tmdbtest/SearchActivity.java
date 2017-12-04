package com.murphy.mike.tmdbtest;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "232f72a9ddf4da165eb64d7ac7168102";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "movie";
    public static String QUERY;

    private RecyclerView recyclerView;
    private CustomSearchAdapter adapter;
    private List<SearchMovieResults.ResultsBean> listOfMovies;
    private SearchMovieResults results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        QUERY = getIntent().getStringExtra("QUERY");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        toolbar.setTitle(QUERY);

        return true;
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadSearchResults();
    }

    private void loadSearchResults(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<SearchMovieResults> call = myInterface.getSearchResults(CATEGORY,API_KEY,LANGUAGE,QUERY,PAGE);

        call.enqueue(new Callback<SearchMovieResults>() {
            @Override
            public void onResponse(Call<SearchMovieResults> call, Response<SearchMovieResults> response) {
                results = response.body();
                setResultsNum();
                listOfMovies = results.getResults();
                adapter = new CustomSearchAdapter(listOfMovies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SearchMovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setResultsNum(){
        String fullString = results.getTotal_results() + " Results Found";
        TextView resultsText = (TextView)findViewById(R.id.NumResults);
        resultsText.setText(fullString);
        resultsText.setVisibility(View.VISIBLE);
    }
}
