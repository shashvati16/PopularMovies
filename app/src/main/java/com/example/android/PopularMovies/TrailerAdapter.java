package com.example.android.PopularMovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.PopularMovies.Data.Trailer;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class TrailerAdapter extends ArrayAdapter {
    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private Trailer[] trailerList;

    private Context context;
    private LayoutInflater layoutInflater;

    public TrailerAdapter(Activity context, Trailer[] trailerList) {
        super(context,0,trailerList);
        this.context = context;
        this.trailerList=trailerList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View addView, ViewGroup parent) {

        Trailer eachTrailer=trailerList[position];

        if(addView==null){
            addView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_list_item, parent, false);
        }
        TextView trailer_label= (TextView) addView.findViewById(R.id.trailer_video_label);
        ImageButton trailer_video= (ImageButton) addView.findViewById(R.id.trailer_videos);
        trailer_label.setText(eachTrailer.getTrailerName());

        trailer_label.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String trailerKey= trailerList[position].getTrailerKey();
                Intent playintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerKey));
                playintent.putExtra("key",trailerKey);
                getContext().startActivity(playintent);
            }
        });
        return addView;
    }
}
