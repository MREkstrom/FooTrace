package com.example.michael.footrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import static com.example.michael.footrace.MainActivity.button_sound;

/**
 * Created by Dan on 12/6/2016.
 */
public class BroadcastGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.cancelButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }
        TextView[] views = {(TextView) findViewById(R.id.broadcastText)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Do nothing
            }

            public void onFinish() {
                //Display alert dialog
                new AlertDialog.Builder(BroadcastGame.this)
                    .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(BroadcastGame.this), getAssets(), "Players Found!"))
                    .setPositiveButton("Start Game!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Play actual game
                            button_sound.start();
                            Intent intent = getIntent();
                            String chosenPath = intent.getStringExtra("PathName");
                            playGame(chosenPath);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel Game", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //cancel hosting
                            finish();
                        }
                    }).show();

            }
        }.start();

    }

    //Cancels Hosting
    public void cancelHost(){
        button_sound.start();
        finish();
    }

    public void playGame(String chosenPath){
        /*Store path name and the mode (Single/multiplayer)*/
        Intent playIntent = new Intent(BroadcastGame.this, PlayGame.class);
        playIntent.putExtra("Mode","MP"); //SP for singleplayer, MP for multiplayer
        playIntent.putExtra("PathName", chosenPath);
        startActivityForResult(playIntent, MainActivity.REQUEST_PLAY_GAME);
    }
}
