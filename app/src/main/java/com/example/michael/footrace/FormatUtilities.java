package com.example.michael.footrace;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Michael on 11/30/2016.
 *
 * Contains various formatting utilities for text found throughout the app. This is useful
 * for when text is generated outside of the XML, or a custom font is required
 */

public class FormatUtilities {

    //Generates a dialog text title with the given text
    public static TextView makeDialogTitle(TextView base, AssetManager assets, String text){
        base.setText(text);
        base.setTypeface(Typeface.createFromAsset(assets, "mvboli.ttf"));
        base.setTextSize(25f);
        base.setGravity(Gravity.CENTER_HORIZONTAL);
        return base;
    }
}
