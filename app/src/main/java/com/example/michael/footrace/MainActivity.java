package com.example.michael.footrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_DESIGN = 1;

    // TODO- global settings values and set/get methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                break;
            case R.id.settingsButton:
                nextScreen.setClass(MainActivity.this,Settings.class);
                draw = true;
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
            addNewDesign();
        }
    }

    public static void addNewDesign(){
        // TODO - Add new design to list
    }

}
