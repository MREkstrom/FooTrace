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
 * Created by SCheng on 12/4/2016.
 */

public class Results extends AppCompatActivity{

    private TextView _timeResultValue;
    private Button _doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String time_value = getIntent().getStringExtra("time_results");

        _timeResultValue = (TextView) findViewById(R.id.time_value);
        System.out.println(time_value);
        _timeResultValue.setText(time_value);

        _doneButton = (Button) findViewById(R.id.reults_done_button);
        _doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                button_sound.start();
                finish();
            }
        });

    }
}