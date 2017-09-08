package com.example.android.PopularMovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.android.PopularMovies.Data.Reviews;
import com.example.android.PopularMovies.Utilities.MoviesUtil;
import com.example.android.PopularMovies.Utilities.NetworkUtil;

import java.net.URL;

public class ReviewActivity extends AppCompatActivity implements LoaderCallbacks<Reviews[]> {
    private static final int REVIEW_LOADER_ID = 0;
    private ListView reviewListView;
    String movie_id;
    private ReviewAdapter mReviewAdapter;
    private Reviews[] mReview;
    private Parcelable[] reviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null || !savedInstanceState.containsKey("reviews")) {
            reviewsList = (Parcelable[])mReview;
        }
        else {
            reviewsList = savedInstanceState.getParcelableArray("reviews");
        }

        setContentView(R.layout.review_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reviewListView= (ListView) findViewById(R.id.review_list);
        Intent intent=getIntent();
        movie_id=intent.getStringExtra("movieid");
        getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, ReviewActivity.this);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("reviews",reviewsList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public Loader<Reviews[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Reviews[]>(this) {

            @Override
            protected void onStartLoading() {
                if (mReview != null) {
                    deliverResult(mReview);
                } else {
                    forceLoad();
                }
            }
            @Override
            public Reviews[] loadInBackground() {
                mReview=new Reviews[20];
                String reviewResults=null;
                URL reviewURL= NetworkUtil.buildReviewsUrl(movie_id);
                try {
                    reviewResults=NetworkUtil.getResponseFromHttpUrl(reviewURL);
                    mReview= MoviesUtil.getReviewObjectsFromJson(reviewResults);
                    return mReview;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }
            public void deliverResult(Reviews[] reviewData) {
                mReview=reviewData;
                super.deliverResult(reviewData);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Reviews[]> loader, Reviews[] reviewData) {
        mReviewAdapter=new ReviewAdapter(ReviewActivity.this,reviewData);
        reviewListView.setAdapter(mReviewAdapter);

    }



    @Override
    public void onLoaderReset(Loader<Reviews[]> loader) {

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
