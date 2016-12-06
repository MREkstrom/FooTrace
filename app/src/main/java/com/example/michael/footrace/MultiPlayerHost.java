package com.example.michael.footrace;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MultiPlayerHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_host);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.mpDrawButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }
        TextView[] views = {(TextView) findViewById(R.id.mpMainTitle)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }
    }

    public void newDesign(View v){
        Intent designIntent = new Intent(MultiPlayerHost.this, NewDesign.class);
        startActivity(designIntent);
    }
}
