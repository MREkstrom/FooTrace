package com.example.michael.footrace;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MultiPlayerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_main);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.joinButton),
                (Button) findViewById(R.id.hostButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }

        TextView[] views = {(TextView) findViewById(R.id.mpMainTitle)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }
    }

    //Handle execution of button clicks for Multiplayer main menu
    public void onMPMainButtonClick(View v){

        Intent nextScreen = new Intent();

        switch(v.getId()){
            case R.id.joinButton:
                nextScreen.setClass(MultiPlayerMain.this, MultiPlayerJoin.class);
                break;
            case R.id.hostButton:
                nextScreen.setClass(MultiPlayerMain.this, MultiPlayerHost.class);
                break;
        }

        startActivity(nextScreen);

    }
}
