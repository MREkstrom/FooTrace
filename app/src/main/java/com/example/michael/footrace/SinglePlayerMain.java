package com.example.michael.footrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SinglePlayerMain extends AppCompatActivity {

    // TODO - extend ListViewActivity? Need some way to implement the design list.
    // If use ListViewActivity (and maybe for all methods), must add a custom Adapter
    // and Design class to put into list. Also applies to MultiPlayerHost.

    // Clicking on a design involves the following commented out code:

//    Intent traceIntent = new Intent(SinglePlayerMain.this, PlayGame.class);
//    traceIntent.putExtra("trace",<design object?>)
//    startActivity(traceIntent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_main);
    }

    public void newDesign(View v){
        Intent designIntent = new Intent(SinglePlayerMain.this, NewDesign.class);
        startActivityForResult(designIntent,MainActivity.REQUEST_NEW_DESIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.REQUEST_NEW_DESIGN && resultCode == RESULT_OK && null != data) {
            MainActivity.addNewDesign(data);
        }


    }
}
