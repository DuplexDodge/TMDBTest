package com.murphy.mike.tmdbtest;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<PopularMovieResults.ResultsBean> listOfMovies;

    private static boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        Toolbar titleBar = (Toolbar) findViewById(R.id.titleBar);
        setSupportActionBar(titleBar);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView)item.getActionView();

        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(200);

        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(150);
        fadeOut.setDuration(150);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("QUERY", query);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                searchView.setQuery("",false);
                searchView.clearFocus();
                item.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    if(!isVisible) {
                        LinearLayout holderLayout = (LinearLayout) findViewById(R.id.holderLayout);
                        holderLayout.startAnimation(fadeIn);
                        holderLayout.setVisibility(View.VISIBLE);
                        isVisible = true;
                    }
                }else{
                    if(isVisible) {
                        LinearLayout holderLayout = (LinearLayout) findViewById(R.id.holderLayout);
                        holderLayout.startAnimation(fadeOut);
                        holderLayout.setVisibility(View.INVISIBLE);
                        isVisible = false;
                    }
                }

                return false;
            }
        });

        return true;
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

    public Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
