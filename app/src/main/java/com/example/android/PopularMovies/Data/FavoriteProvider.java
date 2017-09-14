package com.example.android.PopularMovies.Data;

/**
 * Created by Shashvati on 9/13/2017.
 */



import android.content.UriMatcher;
import android.graphics.Path;
import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = FavoriteProvider.AUTHORITY, database = MoviesDatabase.class)
public class FavoriteProvider {
    public static final String AUTHORITY = "com.example.android.PopularMovies.Data.FavoriteProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String MOVIES = "movies";

    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MoviesDatabase.Movies)
    public static class Movie {
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movies",
                defaultSort = MoviesDatabase.MovieColumns.COLUMN_MOVIE_ID + " ASC")
        public static final Uri MOVIES = Uri.parse("content://" + AUTHORITY + "/movies");

        @InexactContentUri(
                path = Path.MOVIES + "/#",
                name = "MOVIE_ID",
                type = "vnd.android.cursor.dir/movies",
                whereColumn = MoviesDatabase.MovieColumns.COLUMN_MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/movies/" + id);
        }
    }
}
