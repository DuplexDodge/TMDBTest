package com.murphy.mike.tmdbtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieDetailFragment extends Fragment {

    public interface UpdateFrag{
        public void updateFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

       // rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //return rootView;
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    public static MovieDetailFragment newInstance() {
        Bundle args = new Bundle();
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
