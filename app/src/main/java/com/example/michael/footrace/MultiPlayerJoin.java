package com.example.michael.footrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.example.michael.footrace.MainActivity.button_sound;

public class MultiPlayerJoin extends AppCompatActivity {

    private ArrayList<String> traceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_join);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        TextView[] views = {(TextView) findViewById(R.id.broadcastText)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }
        Button[] buttons = {(Button) findViewById(R.id.cancelButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }

        displayTraces(MainActivity.userTraces);
        final CharSequence[] pathList = traceList.toArray(new CharSequence[traceList.size()]);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Do nothing
            }

            public void onFinish() {
                //Display alert dialog
                new AlertDialog.Builder(MultiPlayerJoin.this)
                        .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(MultiPlayerJoin.this), getAssets(), "Games Found:"))
                        .setItems(pathList,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int which){
                                        switch (which) {
                                            case 0:
                                                button_sound.start();
                                                playGame(traceList.get(0));
                                                finish();
                                                break;
                                        }
                                    }

                        }).show();


            }
        }.start();
    }

    public void playGame(String chosenPath){
        /*Store path name and the mode (Single/multiplayer)*/
        Intent playIntent = new Intent(MultiPlayerJoin.this, PlayGame.class);
        playIntent.putExtra("Mode","MP"); //SP for singleplayer, MP for multiplayer
        playIntent.putExtra("PathName", chosenPath);
        startActivityForResult(playIntent, MainActivity.REQUEST_PLAY_GAME);
    }

    private void displayTraces(HashMap<String, UserTrace> traces) {
        Set<String> paths = traces.keySet();

        for(String name : paths){
            traceList.add(name);
            //traces.get(name).getPath();
        }
    }

    //Cancels Joining
    public void cancelJoin(View v){
        button_sound.start();
        finish();
    }

}
