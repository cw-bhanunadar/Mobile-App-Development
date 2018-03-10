package com.example.bhanu.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.model.Review;

import java.util.ArrayList;

/**
 * Created by Bhanugoban Nadar on 2/16/2018.
 */

public class Review_Adapter extends ArrayAdapter<Review> {

    public Review_Adapter(Activity context, ArrayList<Review> words) {
        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_list, parent, false);


        }
        Review currentWord = getItem(position);
        TextView miawokTextView = (TextView) listItemView.findViewById(R.id.Name);
        miawokTextView.setText(currentWord.getName());
        TextView email=(TextView) listItemView.findViewById(R.id.display_email);
        email.setText(currentWord.getEmail());
        TextView Rating = (TextView) listItemView.findViewById(R.id.display_rating);
        Rating.setText(currentWord.getRating());
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.display_Review);
        defaultTextView.setText(currentWord.getReview());
        return listItemView;

    }
}
