package com.example.michael.footrace;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MultiPlayerJoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_join);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        TextView[] views = {(TextView) findViewById(R.id.mpMainTitle)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }
    }
}
