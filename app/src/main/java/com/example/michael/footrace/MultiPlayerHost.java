package com.example.michael.footrace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MultiPlayerHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_host);
    }

    public void newDesign(View v){
        Intent designIntent = new Intent(MultiPlayerHost.this, NewDesign.class);
        startActivity(designIntent);
    }
}
