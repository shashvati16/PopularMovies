package com.example.android.PopularMovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.PopularMovies.Data.Reviews;

/**
 * Created by MCLAB on 4/21/2017.
 */

public class ReviewAdapter extends ArrayAdapter {
    private static final String LOG_TAG=ReviewAdapter.class.getSimpleName();
    private Reviews[] reviewsList;

    private Context context;
    private LayoutInflater layoutInflater;

    public ReviewAdapter(Activity context, Reviews[] reviewsList){
        super(context,R.layout.review_list_item,reviewsList);
        this.context=context;
        this.reviewsList=reviewsList;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Reviews eachMoveReview=reviewsList[position];
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.review_list_item,parent,false);
        }
        TextView reviewAuthor= (TextView) convertView.findViewById(R.id.author);
        TextView reviewContent= (TextView) convertView.findViewById(R.id.review_text);
        reviewAuthor.setText(eachMoveReview.getAuthor());
        reviewContent.setText(eachMoveReview.getContent());
        return convertView;
    }
}
