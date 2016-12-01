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

    public static final int REQUEST_NEW_DESIGN = 1;
    public static final int REQUEST_PLAY_GAME = 2;

    // TODO- global settings values and set/get methods
    public static HashMap <String, Path> traces; //contains mappings of path names to traces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        traces = new HashMap <String, Path>();

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

        boolean draw = false;
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
                draw = true;
                break;
            case R.id.settingsButton:
                nextScreen.setClass(MainActivity.this,Settings.class);
                break;
        }

        if(draw){
            startActivityForResult(nextScreen, REQUEST_NEW_DESIGN);
        } else {
            startActivity(nextScreen);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NEW_DESIGN && resultCode == RESULT_OK && null != data) {
            addNewDesign(data);
        }
    }

    public static void addNewDesign(Intent data){
        Parcelable_Path pathInfo = data.getParcelableExtra("PathInfo");
        String path_name = pathInfo.getPathName();
        Path path = pathInfo.getPath();

        traces.put(path_name, path);

        //System.out.println(traces.toString());
    }

}
