package com.example.android.PopularMovies.Utilities;

import com.example.android.PopularMovies.Data.Movies;
import com.example.android.PopularMovies.Data.Reviews;
import com.example.android.PopularMovies.Data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class MoviesUtil {
    public static Movies[] getMovieObjectsFromJson(String resultJsonString) throws JSONException {
        final String poster="poster_path";
        final String plotSummary="overview";
        final String releaseDate="release_date";
        final String movieId="id";
        final String title="title";
        final String userRating="vote_average";

        JSONObject movieObject=new JSONObject(resultJsonString);
        JSONArray movieArray=movieObject.getJSONArray("results");
        int size=movieArray.length();
        Movies[] movieList=new Movies[size];

        for (int i=0;i<20;i++){
            JSONObject movieDetail=movieArray.getJSONObject(i);
            movieList[i]=new Movies();
            movieList[i].setPosterPath(movieDetail.getString(poster));
            movieList[i].setPlotSynopsis(movieDetail.getString(plotSummary));
            movieList[i].setReleaseDate(movieDetail.getString(releaseDate).substring(0,4));
            movieList[i].setMovieId(movieDetail.getLong(movieId));
            movieList[i].setOrignalTitle(movieDetail.getString(title));
            movieList[i].setUserRating(movieDetail.getDouble(userRating));

        }
        return movieList;
    }
    public static Trailer[] getTrailerObjectsFromJson(String trailerJsonString) throws JSONException{
        final String trailerKey="key";
        final String trailerName="name";
        JSONObject trailerObject=new JSONObject(trailerJsonString);
        JSONArray trailerArray=trailerObject.getJSONArray("results");
        Trailer[] trailerList=new Trailer[trailerArray.length()];
        for (int j=0;j<trailerArray.length();j++){
            JSONObject trailerDetail=trailerArray.getJSONObject(j);
            trailerList[j]=new Trailer();
            trailerList[j].setTrailerKey(trailerDetail.getString(trailerKey));
            trailerList[j].setTrailerName(trailerDetail.getString(trailerName));
        }
        return trailerList;
    }
    public static Reviews[] getReviewObjectsFromJson(String reviewsJsonString) throws JSONException{
        final String authorName="author";
        final String reviewContent="content";
        JSONObject reviewObject=new JSONObject(reviewsJsonString);
        JSONArray reviewsArray=reviewObject.getJSONArray("results");
        Reviews[] reviewsList=new Reviews[reviewsArray.length()];
        for(int k=0;k<reviewsArray.length();k++){
            JSONObject reviewDetail=reviewsArray.getJSONObject(k);
            reviewsList[k]=new Reviews();
            reviewsList[k].setAuthor(reviewDetail.getString(authorName));
            reviewsList[k].setContent(reviewDetail.getString(reviewContent));
        }
        return reviewsList;
    }
}
