package com.murphy.mike.tmdbtest;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/{category}")
    Call<PopularMovieResults> getPopularMovies(
            @Path("category")String category,
            @Query("api_key")String apiKey,
            @Query("language")String language,
            @Query("page")int page
    );

    @GET("/3/search/{category}")
    Call<SearchMovieResults> getSearchResults(
            @Path("category")String category,
            @Query("api_key")String apiKey,
            @Query("language")String language,
            @Query("query")String query,
            @Query("page")int page
    );

    @GET("/3/movie/{movieID}/{category}")
    Call<GetMovieCast> getMovieCast(
            @Path("movieID")int movieID,
            @Path("category")String category,
            @Query("api_key")String apiKey
    );
}
