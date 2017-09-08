package com.example.android.PopularMovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.PopularMovies.Data.Movies;
import com.squareup.picasso.Picasso;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private Movies[] mMoviesData=new Movies[20];
    private MovieAdapterOnClickHandler mClickHandler;



    public interface MovieAdapterOnClickHandler {
        void onClick(Movies movieClicked);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler,Movies[] moviesData){
        mMoviesData=moviesData;
        mClickHandler=clickHandler;
    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView iconView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            iconView = (ImageView) view.findViewById(R.id.movie_posters);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movies movieClicked = mMoviesData[adapterPosition];
            mClickHandler.onClick(movieClicked);
        }
    }
        @Override
        public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context=viewGroup.getContext();
            int layoutIdForGrid=R.layout.movies_grid;
            LayoutInflater inflater=LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately=false;
            View view=inflater.inflate(layoutIdForGrid,viewGroup,shouldAttachToParentImmediately);
            return new MovieAdapterViewHolder(view);

        }
        @Override
        public void onBindViewHolder(MovieAdapterViewHolder mViewHolder, int position) {
            Movies eachMovie=mMoviesData[position];
            if(eachMovie!=null) {
                Picasso.with(mViewHolder.iconView.getContext())
                        .load(eachMovie.getPosterPath()).into(mViewHolder.iconView);
            }
        }
        @Override
        public int getItemCount() {
            if(null==mMoviesData){
                return 0;
            }
            else {
                return mMoviesData.length;
            }

        }
}
