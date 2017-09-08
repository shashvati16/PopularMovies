package com.example.android.PopularMovies.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class Movies implements Parcelable{
    String posterPath;
    String orignalTitle;
    String imageBasePath="https://image.tmdb.org/t/p/w185";
    String plotSynopsis;
    long movieId;
    String releaseDate;
    double userRating;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath =  imageBasePath.concat(posterPath);
    }

    public String getOrignalTitle() {
        return orignalTitle;
    }

    public void setOrignalTitle(String orignalTitle) {
        this.orignalTitle = orignalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
    public Movies(){}
    private Movies(Parcel in){
        movieId=in.readLong();
        posterPath=in.readString();
        orignalTitle=in.readString();
        plotSynopsis=in.readString();
        releaseDate=in.readString();
        userRating=in.readDouble();

    }
    @Override
    public int describeContents() {
        return 0;
    }
    public String toString() {
        return movieId + "--" + posterPath + "--" + orignalTitle + "--" + plotSynopsis + "--" + releaseDate +
                "--" + userRating;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeLong(movieId);
        parcel.writeString(posterPath);
        parcel.writeString(orignalTitle);
        parcel.writeString(plotSynopsis);
        parcel.writeString(releaseDate);
        parcel.writeDouble(userRating);
    }
    public final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel parcel){
            return new Movies(parcel);
        }
        @Override
        public Movies[] newArray(int i){
            return new Movies[i];
        }

    };
}
