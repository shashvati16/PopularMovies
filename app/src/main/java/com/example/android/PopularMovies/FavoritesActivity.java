package com.example.android.PopularMovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.PopularMovies.Data.MovieFavoriteContract;

public class FavoritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG=FavoritesActivity.class.getSimpleName();

    RecyclerView fav_list;
    private static final int FAV_LOADER_ID = 0;
    FavoriteAdapter favAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fav_list=(RecyclerView) findViewById(R.id.fav_list);
        fav_list.setLayoutManager(new LinearLayoutManager(this));
        favAdapter=new FavoriteAdapter(this);
        fav_list.setAdapter(favAdapter);
        getSupportLoaderManager().initLoader(FAV_LOADER_ID, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mFavData = null;
            @Override
            protected void onStartLoading() {
                if (mFavData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieFavoriteContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_ID);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                mFavData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
