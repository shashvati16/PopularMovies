package com.example.android.PopularMovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.PopularMovies.Data.Movies;
import com.example.android.PopularMovies.Utilities.MoviesUtil;
import com.example.android.PopularMovies.Utilities.NetworkUtil;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movies[]>,
        MovieAdapter.MovieAdapterOnClickHandler  {
    private RecyclerView mRecyclerView;
    private Movies[] mMoviesData;
    private Parcelable[] moviesList;
    private MovieAdapter mMovieAdapter;
    String movieType;
    private static final int MOVIE_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            moviesList = (Parcelable[]) mMoviesData;
        }
        else {
            moviesList = savedInstanceState.getParcelableArray("movies");
        }
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_grid);

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }


        mRecyclerView.setHasFixedSize(true);
        movieType="popular";
        LoaderManager.LoaderCallbacks<Movies[]> callback = MainActivity.this;
        Bundle bundleforLoader=null;
        int loaderId=MOVIE_LOADER_ID;
        getSupportLoaderManager().initLoader(loaderId,bundleforLoader,callback);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("mMoviesData",moviesList);
        super.onSaveInstanceState(outState);
    }



    @Override
    public Loader<Movies[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Movies[]>(this) {
            Movies[] moviesData;

            @Override
            protected void onStartLoading() {
                if (moviesData != null) {
                    deliverResult(moviesData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Movies[] loadInBackground() {
                moviesData=new Movies[20];
                URL movieSearchUrl = NetworkUtil.buildUrl(movieType);
                String movieResults=null;
                try {
                    if(isOnline()==true) {
                        movieResults = NetworkUtil.getResponseFromHttpUrl(movieSearchUrl);
                    }
                    moviesData = MoviesUtil.getMovieObjectsFromJson(movieResults);
                    return moviesData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            public boolean isOnline() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    return (exitValue == 0);
                }
                catch (IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }

                return false;
            }
            public void deliverResult(Movies[] moviesData){
                moviesData=moviesData;
                super.deliverResult(moviesData);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Movies[]> loader, Movies[] moviesdata) {
        mMoviesData=moviesdata;
        mMovieAdapter=new MovieAdapter(MainActivity.this,moviesdata);
        mRecyclerView.setAdapter(mMovieAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Movies[]> loader) {


    }

    @Override
    public void onClick(Movies movieClicked) {
        Context context=this;
        Class destinationClass=DetailActivity.class;
        Intent intentToStartDetailActivity=new Intent(context,destinationClass);
        intentToStartDetailActivity.putExtra("movieId",String.valueOf(movieClicked.getMovieId()));
        intentToStartDetailActivity.putExtra("movieTitle",movieClicked.getOrignalTitle());
        intentToStartDetailActivity.putExtra("moviePoster",movieClicked.getPosterPath());
        intentToStartDetailActivity.putExtra("moviePlot",movieClicked.getPlotSynopsis());
        intentToStartDetailActivity.putExtra("userRating",String.valueOf(movieClicked.getUserRating()));
        intentToStartDetailActivity.putExtra("releaseDate",movieClicked.getReleaseDate());
        startActivity(intentToStartDetailActivity);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_mostpopular) {
            movieType="popular";
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,MainActivity.this);
            return true;
        }
        if (id == R.id.action_toprated) {
            movieType="top_rated";
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,MainActivity.this);
            return true;
        }
        if(id==R.id.favorites){
            Intent openFavorites=new Intent(MainActivity.this,FavoritesActivity.class);
            startActivity(openFavorites);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}

