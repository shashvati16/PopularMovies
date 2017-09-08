package com.example.android.PopularMovies.Utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.PopularMovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();
    private final static String baseUrl="https://api.themoviedb.org/3/movie/";

    private final static String PARAM_Language="en-US";
    private final static String page="page";
    private final static String PARAM_PAGE="1";
    private final static String pLanguage="language";

    private final static String PARAM_API= BuildConfig.THE_MOVIE_DB_API_KEY;
    private final static String apiKey="api_key";
    private final static String PARAMVIDEOS="/videos";
    private final static String PARAMREVIEWS="/reviews";
    public static URL buildUrl(String movieType){
        Uri builtUri=Uri.parse(baseUrl.concat(movieType)).buildUpon()
                .appendQueryParameter(apiKey,PARAM_API)
                .appendQueryParameter(pLanguage,PARAM_Language)
                .appendQueryParameter(page,PARAM_PAGE).build();
        URL url=null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;

    }

    public static URL buildTrailerUrl(String movieId){
        String trailerUrl=baseUrl.concat(String.valueOf(movieId));
        Uri builtUri=Uri.parse(trailerUrl.concat(PARAMVIDEOS)).buildUpon()
                .appendQueryParameter(apiKey,PARAM_API)
                .appendQueryParameter(pLanguage,PARAM_Language)
                .build();
        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }
    public static URL buildReviewsUrl(String movieId){
        Uri builtUri=Uri.parse(baseUrl+movieId+PARAMREVIEWS).buildUpon()
                .appendQueryParameter(apiKey,PARAM_API)
                .appendQueryParameter(pLanguage,PARAM_Language)
                .build();
        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
