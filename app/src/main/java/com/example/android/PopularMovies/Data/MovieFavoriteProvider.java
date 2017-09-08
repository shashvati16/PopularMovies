package com.example.android.PopularMovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.PopularMovies.Data.MovieFavoriteContract.MovieEntry.TABLE_NAME;

/**
 * Created by MCLAB on 4/23/2017.
 */

public class MovieFavoriteProvider extends ContentProvider {
    MovieFavoriteDBHelper movieDBHelper;
    private static final int CODE_MOVIES=100;
    private static final int CODE_MOVIES_ID=101;
    private static UriMatcher mUriMatcher=buildUriMatcher();
    @Override
    public boolean onCreate() {
        movieDBHelper=new MovieFavoriteDBHelper(getContext());
        return true;
    }
    public static UriMatcher buildUriMatcher(){
        mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(MovieFavoriteContract.CONTENT_AUTHORITY,MovieFavoriteContract.PATH_MOVIE,CODE_MOVIES);
        mUriMatcher.addURI(MovieFavoriteContract.CONTENT_AUTHORITY,MovieFavoriteContract.PATH_MOVIE +  "/#", CODE_MOVIES_ID);
        return mUriMatcher;
    }
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=movieDBHelper.getReadableDatabase();
        int match=mUriMatcher.match(uri);
        Cursor queryMovies;
        switch (match){
            case CODE_MOVIES:
                queryMovies=db.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CODE_MOVIES_ID:
                String movieString=uri.getLastPathSegment();
                String mSelection=MovieFavoriteContract.MovieEntry._ID + "=?";
                String[] mSelectionArgs=new String[]{movieString};
                queryMovies=db.query(TABLE_NAME,projection,mSelection,mSelectionArgs,null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        queryMovies.setNotificationUri(getContext().getContentResolver(),uri);
        return queryMovies;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Unknown Operation.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (mUriMatcher.match(uri)) {
            case CODE_MOVIES:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieFavoriteContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        int movieDeleted;
        switch (mUriMatcher.match(uri)) {
            case CODE_MOVIES_ID:
                try {
                    db.beginTransaction();
                    String id = uri.getPathSegments().get(1);
                    String mSelection = MovieFavoriteContract.MovieEntry.COLUMN_MOVIE_ID + "=?";
                    String[] mSelectionArgs = new String[]{id};
                    movieDeleted=db.delete(TABLE_NAME,mSelection,mSelectionArgs);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();

                }
                if (movieDeleted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return movieDeleted;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
    }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri:" + uri);

    }
}
