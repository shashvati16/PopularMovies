package com.example.android.PopularMovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.PopularMovies.Data.MovieFavoriteContract;
import com.example.android.PopularMovies.Data.Trailer;
import com.example.android.PopularMovies.Utilities.MoviesUtil;
import com.example.android.PopularMovies.Utilities.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Trailer[]>,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private TextView movieTitle;
    private ImageView movieThumbNail;
    private TextView moviePlot;
    private TextView userRating;
    private TextView releaseDate;
    private TextView movieReviews;
    private ListView trailerListView;

    private TrailerAdapter mTrailerAdapter;
    private ToggleButton favoriteButton;
    private TextView trailer_title;
    private String movieId;
    private Trailer[] mTrailer;
    private Parcelable[] trailerList;
    private String posterPath;

    private static final int TRAILER_LOADER_ID = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("trailer")) {
            trailerList = (Parcelable[])mTrailer;
        }
        else {
            trailerList = savedInstanceState.getParcelableArray("trailer");
        }
        setContentView(R.layout.activity_detail);
        movieTitle = (TextView) findViewById(R.id.movie_title_text);
        movieThumbNail = (ImageView) findViewById(R.id.movie_thumbnail);
        moviePlot = (TextView) findViewById(R.id.movie_plot);
        userRating = (TextView) findViewById(R.id.user_rating);
        releaseDate = (TextView) findViewById(R.id.release_date);
        favoriteButton = (ToggleButton) findViewById(R.id.favourite_btn);

        favoriteButton.setOnCheckedChangeListener(this);

        trailer_title= (TextView) findViewById(R.id.trailer_title);

        trailerListView = (ListView) findViewById(R.id.trailer_list);
        movieReviews=(TextView)findViewById(R.id.movie_reviews);
        movieReviews.setOnClickListener(this);
        Intent i = getIntent();
        if((i!=null) && (i.getExtras()!=null)) {
            movieTitle.setText(i.getStringExtra("movieTitle"));
            posterPath = i.getStringExtra("moviePoster");
            Picasso.with(getApplicationContext()).load(posterPath).into(movieThumbNail);
            moviePlot.setText(i.getStringExtra("moviePlot"));
            userRating.setText(i.getStringExtra("userRating") + "\\10");
            releaseDate.setText(i.getStringExtra("releaseDate"));
            movieId = i.getStringExtra("movieId");
            SharedPreferences shared = getSharedPreferences("FAVORITE", MODE_PRIVATE);
            boolean state = shared.contains(movieId);
            favoriteButton.setChecked(state);
            Bundle bundleForLoader = null;
            getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, bundleForLoader, DetailActivity.this);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("trailer",trailerList);
        super.onSaveInstanceState(outState);
    }



    @Override
    public Loader<Trailer[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Trailer[]>(this) {


            @Override
            protected void onStartLoading() {
                if (mTrailer!=null){
                    deliverResult(mTrailer);
                }else{
                    forceLoad();
                }
            }

            @Override
            public Trailer[] loadInBackground() {
                mTrailer=new Trailer[20];
                String trailerResults=null;
                URL trailerURL= NetworkUtil.buildTrailerUrl(movieId);
                try {
                    trailerResults=NetworkUtil.getResponseFromHttpUrl(trailerURL);
                    mTrailer= MoviesUtil.getTrailerObjectsFromJson(trailerResults);
                    return mTrailer;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }
            public void deliverResult(Trailer[] trailerData) {
                mTrailer=trailerData;
                super.deliverResult(trailerData);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Trailer[]> loader, Trailer[] trailerData) {
        mTrailerAdapter = new TrailerAdapter(DetailActivity.this, trailerData);
        trailerListView.setAdapter(mTrailerAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Trailer[]> loader) {

    }


    @Override
    public void onClick(View v) {
        Context context=this;
        Class destinationClass=ReviewActivity.class;
        Intent reviewActivity=new Intent(context,destinationClass);
        reviewActivity.putExtra("movieid",movieId);
        startActivity(reviewActivity);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        boolean favOrNot=buttonView.isChecked();
        Long favId=Long.parseLong(movieId);

        if (favOrNot){
            SharedPreferences favorite = getSharedPreferences("FAVORITE",MODE_PRIVATE);
            SharedPreferences.Editor editor = favorite.edit();
            editor.putLong(favId.toString(),favId);
            editor.commit();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_ID,favId);
            contentValues.put(MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_TITLE,movieTitle.getText().toString());
            contentValues.put(MovieFavoriteContract.MovieEntry.COLUMN_POSTER_PATH,posterPath);
            Uri uri = getContentResolver().insert(MovieFavoriteContract.MovieEntry.CONTENT_URI, contentValues);
            if(uri != null) {
                Toast.makeText(getBaseContext(), movieTitle.getText().toString() + " added to Favorites", Toast.LENGTH_LONG).show();
            }

        }
        else{
            SharedPreferences favorite = getSharedPreferences("FAVORITE",MODE_PRIVATE);
            SharedPreferences.Editor editor = favorite.edit();
            editor.remove(favId.toString());
            editor.commit();
            Uri uri = MovieFavoriteContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(movieId).build();
            int rowDeleted=getContentResolver().delete(uri, null, null);
            if(rowDeleted!=0) {
                Toast.makeText(getBaseContext(), movieTitle.getText().toString() + " removed from Favorites", Toast.LENGTH_LONG).show();
            }
        }

    }
}

