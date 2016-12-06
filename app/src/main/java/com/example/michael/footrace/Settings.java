package com.example.michael.footrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private String _username;
    private String _email;
    private int _red;
    private int _green;
    private int _blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*display user profile info*/
        _username = MainActivity.prof.get_displayName();
        _email = MainActivity.prof.get_emailAddress();
        _red = MainActivity.prof.get_red();
        _green = MainActivity.prof.get_green();
        _blue = MainActivity.prof.get_blue();

        TextView swatch = (TextView)findViewById(R.id.colorSwatch);
        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));

        TextView displayNameView = (TextView)findViewById(R.id.displayName);
        displayNameView.setText(_username);

        TextView emailView = (TextView)findViewById(R.id.emailAddress);
        emailView.setText(_email);
    }

    public void goBack(View view) {
        finish();
    }

    public void editSettings(View view) {
        final int temp_red = _red;
        final int temp_green = _green;
        final int temp_blue = _blue;

        AlertDialog.Builder builder;
        LayoutInflater inflater;
        AlertDialog alertDialog;

        //builds alert dialog for clearing
        builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        inflater = this.getLayoutInflater();

        // Inflate the dialog_settings.xml layout and create the View
        final View settingsDialogView = inflater.inflate(R.layout.dialog_settings, null);

        //Sets color swatch to selected path color
        TextView swatch = (TextView)settingsDialogView.findViewById(R.id.colorSwatch);
        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));

        /*Set cursor to end of edit text view*/
        EditText dispText = (EditText) settingsDialogView.findViewById(R.id.displayName);
        dispText.setText(_username);
        dispText.setSelection(dispText.getText().length());

        /*Set cursor to end of edit text view*/
        EditText emailText = (EditText) settingsDialogView.findViewById(R.id.emailAddress);
        emailText.setText(_email);
        emailText.setSelection(emailText.getText().length());

        // Get access to the seekbar on this dialog.
        SeekBar seekBar = (SeekBar)settingsDialogView.findViewById(R.id.seekBarRed);

        // Set progress bar to current color value
        seekBar.setProgress(_red);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //Updates red to value shown on seekbar
            //Updates swatch in real time
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                _red = progress;
                TextView swatch = (TextView)settingsDialogView.findViewById(R.id.colorSwatch);
                swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        });

        // Get access to the seekbar on this dialog.
        seekBar = (SeekBar)settingsDialogView.findViewById(R.id.seekBarGreen);

        // Set progress bar to current color value
        seekBar.setProgress(_green);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Updates green to value shown on seekbar
                //Updates swatch in real time
                _green = progress;
                TextView swatch = (TextView)settingsDialogView.findViewById(R.id.colorSwatch);
                swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        });

        // Get access to the seekbar on this dialog.
        seekBar = (SeekBar)settingsDialogView.findViewById(R.id.seekBarBlue);

        // Set progress bar to current color value
        seekBar.setProgress(_blue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Updates blue to value shown on seekbar
                //Updates swatch in real time
                _blue = progress;
                TextView swatch = (TextView)settingsDialogView.findViewById(R.id.colorSwatch);
                swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        });

        // This is the method that allows us to use our own custom view. We set the AlertDialog builder
        // to the view we created with the inflater above
        builder.setView(settingsDialogView)
                .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(this), getAssets(), "Edit Settings"))
                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*Updates profile information*/
                        EditText user = (EditText)settingsDialogView.findViewById(R.id.displayName);
                        _username = user.getText().toString();
                        TextView dispText = (TextView) findViewById(R.id.displayName);
                        dispText.setText(_username);

                        EditText email = (EditText)settingsDialogView.findViewById(R.id.emailAddress);
                        _email = email.getText().toString();
                        TextView emailText = (TextView) findViewById(R.id.emailAddress);
                        emailText.setText(_email);

                        TextView swatch = (TextView) findViewById(R.id.colorSwatch);
                        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));

                        MainActivity.prof.updateProfile(_username, _email, _red, _green, _blue);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resets colors to former values
                        _red = temp_red;
                        _green = temp_green;
                        _blue = temp_blue;
                    }
                });

        alertDialog = builder.create();
        alertDialog.show();
    }
}
