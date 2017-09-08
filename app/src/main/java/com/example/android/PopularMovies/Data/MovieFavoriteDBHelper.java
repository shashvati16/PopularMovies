package com.example.android.PopularMovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MCLAB on 4/23/2017.
 */

public class MovieFavoriteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="movies.db";

    private static final int DATABASE_VERSION = 2;
    public MovieFavoriteDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieFavoriteContract.MovieEntry.TABLE_NAME + " (" +
                MovieFavoriteContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_ID + " REAL NOT NULL, " +
                MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_TITLE + " VARCHAR(20) NOT NULL, " +
                MovieFavoriteContract.MovieEntry.COLUMN_POSTER_PATH + " VARCHAR(50) NOT NULL, " +
                " UNIQUE (" + MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieFavoriteContract.MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
