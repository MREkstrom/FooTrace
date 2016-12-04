package com.example.michael.footrace;

import android.content.Intent;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /* Global variables*/
    public static final int REQUEST_NEW_DESIGN = 1;
    public static final int REQUEST_PLAY_GAME = 2;

    public static HashMap <String, Path> traces; //contains mappings of path names to traces
    public static UserProfile prof; // contains user profile information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Initialize globals*/
        traces = new HashMap <String, Path>();
        prof = new UserProfile("User", "Email@email.com");

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.spButton),
                (Button) findViewById(R.id.mpButton),
                (Button) findViewById(R.id.drawButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }

    }

    //Handle execution of button clicks for main menu
    public void onMainMenuButtonClick(View v){

        Intent nextScreen = new Intent();

        switch(v.getId()){
            case R.id.spButton:
                nextScreen.setClass(MainActivity.this,SinglePlayerMain.class);
                break;
            case R.id.mpButton:
                nextScreen.setClass(MainActivity.this,MultiPlayerMain.class);
                break;
            case R.id.drawButton:
                nextScreen.setClass(MainActivity.this,NewDesign.class);
                break;
            case R.id.settingsButton:
                nextScreen.setClass(MainActivity.this,Settings.class);
                break;
        }

        startActivity(nextScreen);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PLAY_GAME && resultCode == RESULT_OK && null != data) {
            //TODO: If necessary, process result of game
        }
    }

}
