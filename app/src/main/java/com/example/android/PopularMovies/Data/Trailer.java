package com.example.android.PopularMovies.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class Trailer implements Parcelable {
    String trailerKey;
    String trailerName;
    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public Trailer(){}
    private Trailer(Parcel in){
        trailerKey=in.readString();
        trailerName=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return trailerKey + "--" + trailerName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(trailerKey);
        parcel.writeString(trailerName);
    }
    public final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel){
            return new Trailer(parcel);
        }
        @Override
        public Trailer[] newArray(int i){
            return new Trailer[i];
        }

    };


}
