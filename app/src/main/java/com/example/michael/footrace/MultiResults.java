package com.example.michael.footrace;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.michael.footrace.MainActivity.button_sound;

/**
 * Created by SCheng on 12/11/2016.
 */

public class MultiResults extends AppCompatActivity{
    private Button _doneButton;
    private Button _retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_results);

        Intent resultIntent = getIntent();
        String time_value = resultIntent.getStringExtra("time_results");
        final String pathName = resultIntent.getStringExtra("PathName");

        _doneButton = (Button) findViewById(R.id.reults_done_button);
        _doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                button_sound.start();
                finish();
            }
        });

        _retryButton = (Button) findViewById(R.id.results_retry_button);
        _retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent retryIntent = new Intent(MultiResults.this, PlayGame.class);
                retryIntent.putExtra("Mode","MP").putExtra("PathName",pathName);
                startActivity(retryIntent);
                finish();
            }
        });


        //Set typeface of buttons and text views
        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.reults_done_button)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }
        TextView[] views = {(TextView) findViewById(R.id.Results),
                (TextView) findViewById(R.id.time_results_heading),
                (TextView) findViewById(R.id.accuracy_reults_heading),
                (TextView) findViewById(R.id.score_results_heading)};
        for (TextView tv : views) {
            tv.setTypeface(tf);

        }

    }
}
