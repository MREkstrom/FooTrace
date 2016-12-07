package com.example.michael.footrace;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.HashMap;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    /* Global variables*/
    public static final int REQUEST_NEW_DESIGN = 1;
    public static final int REQUEST_PLAY_GAME = 2;
    public static final int REQUEST_BROADCAST = 3;

    public static HashMap <String, UserTrace> userTraces; // contain ending coords of paths
    public static UserProfile prof; // contains user profile information
    public static MediaPlayer button_sound; // creates button press sound
    public static MediaPlayer background_music; // creates background music


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Initialize globals*/
        userTraces = new HashMap <String, UserTrace>();
        prof = new UserProfile("User", "Email@email.com");

        button_sound = MediaPlayer.create(this, R.raw.click3);
        background_music = MediaPlayer.create(this, R.raw.background_music);

        //start background music and allow it to loop
        background_music.setLooping(true);
        background_music.start();

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
                button_sound.start();
                nextScreen.setClass(MainActivity.this,SinglePlayerMain.class);
                break;
            case R.id.mpButton:
                button_sound.start();
                nextScreen.setClass(MainActivity.this,MultiPlayerMain.class);
                break;
            case R.id.drawButton:
                button_sound.start();
                nextScreen.setClass(MainActivity.this,NewDesign.class);
                break;
            case R.id.settingsButton:
                button_sound.start();
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

    @Override
    protected void onPause(){
        super.onPause();
        //background_music.release();
        //button_sound.release();
    }

}
