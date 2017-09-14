package com.example.android.PopularMovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.PopularMovies.Data.MoviesDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by MCLAB on 4/23/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public FavoriteAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favorite_list_item, parent, false);

        return new FavoriteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MoviesDatabase.MovieColumns.COLUMN_MOVIE_ID);
        int titleIndex = mCursor.getColumnIndex(MoviesDatabase.MovieColumns.COLUMN_MOVIE_TITLE);
        int posterIndex = mCursor.getColumnIndex(MoviesDatabase.MovieColumns.COLUMN_POSTER_PATH);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final long id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);
        String posterPath = mCursor.getString(posterIndex);
        //Set values
        holder.itemView.setTag(id);
        holder.movieTitleView.setText(title);
        Picasso.with(mContext).load(posterPath).into(holder.moviePosterView);

    }
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView movieTitleView;
        ImageView moviePosterView;


        public FavoriteViewHolder(View itemView) {
            super(itemView);

            movieTitleView = (TextView) itemView.findViewById(R.id.favorite_titles);
            moviePosterView = (ImageView) itemView.findViewById(R.id.favorite_poster);
        }
    }

}
