package com.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhanu.controller.R;

/**
 * Created by Bhanugoban Nadar on 1/21/2018.
 */

public class Images extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.images_fragment, container, false);

        return rootView;
    }
}