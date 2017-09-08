package com.example.android.PopularMovies.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class Reviews implements Parcelable {
    String author;
    String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Reviews(){}
    private Reviews(Parcel in){
        author=in.readString();
        content=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return author + "--" + content;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(author);
        parcel.writeString(content);
    }
    public final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel parcel){
            return new Reviews(parcel);
        }
        @Override
        public Reviews[] newArray(int i){
            return new Reviews[i];
        }

    };
}
