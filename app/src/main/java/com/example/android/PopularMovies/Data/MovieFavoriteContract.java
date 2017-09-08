package com.example.android.PopularMovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by MCLAB on 4/23/2017.
 */

public class MovieFavoriteContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.PopularMovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE="movies";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movies_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}
