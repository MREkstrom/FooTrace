package com.example.michael.footrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NewDesign extends AppCompatActivity {

    DesignView mDesignView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_design);

        mDesignView = (DesignView) findViewById(R.id.designView);
    }

    public void onDesignButtonClick(View v){

        switch(v.getId()){
            case R.id.colorButton:

                // TODO - open color popup, change color

                break;
            case R.id.clearButton:

                // TODO - clear the design via dialog popup

                break;
            case R.id.saveButton:
                Intent resultIntent = new Intent();

                // TODO - save the design into resultIntent via dialog popup

                setResult(RESULT_OK, resultIntent);
                finish();
                break;
            case R.id.mapToggleButton:

                // TODO - add/remove map to DesignView background

                break;
        }
    }
}
