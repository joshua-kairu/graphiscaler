package com.jlt.graphiscaler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**

 Graphiscaler

 Tutorial for showcasing typographical scale

 Copyright (C) 2016 Kairu Joshua Wambugu

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see http://www.gnu.org/licenses/.

 */

// begin fragment MainActivityFragment
public class MainActivityFragment extends Fragment {

    /** CONSTANTS */

    /** VARIABLES */

    /* TextViews */

    @InjectView( R.id.fm_tv_display4 ) // injecting is binding
    TextView display4TextView; // text view for display 4

    @InjectView( R.id.fm_tv_headline )
    TextView headlineTextView; // text view for the headline

    /* Typefaces */

    private Typeface courgetteTypeface; // the courgette typeface

    /** METHODS */

    /** Getters and Setters */

    /**
     * Overrides
     */

    @Override
    // begin onAttach
    public void onAttach( Activity activity ) {

        // 0. super things
        // 1. initialize courgette typeface from assets

        // 0. super things

        super.onAttach( activity );

        // 1. initialize courgette typeface from assets

        courgetteTypeface = Typeface.createFromAsset( getActivity().getAssets(), "Courgette-Regular.ttf" );

    } // end onAttach

    @Override
    // begin onCreateView
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        // 0. inflate using the main fragment layout
        // 1. bind the selected views to those inflated
        // 2. set the views to have the courgette typeface
        // 3. return the inflated view

        // 0. inflate using the main fragment layout

        View rootView = inflater.inflate( R.layout.fragment_main, container, false );

        // 1. bind the selected views to those inflated

        ButterKnife.inject( this, rootView );

        // 2. set the views to have the courgette typeface

        display4TextView.setTypeface( courgetteTypeface );
        headlineTextView.setTypeface( courgetteTypeface );

        // 3. return the inflated view

        return rootView;

    } // end onCreateView

    /** Other Methods */

} // end fragment MainActivityFragment