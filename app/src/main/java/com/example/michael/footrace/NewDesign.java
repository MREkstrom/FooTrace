package com.example.michael.footrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.michael.footrace.MainActivity.button_sound;

public class NewDesign extends AppCompatActivity {

    /*Instance variables*/
    DesignView mDesignView;
    private int _red = 0;
    private int _green = 0;
    private int _blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_design);

        mDesignView = (DesignView) findViewById(R.id.designView);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.saveButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }
    }

    public void onDesignButtonClick(View v){

        AlertDialog.Builder builder;
        LayoutInflater inflater;
        AlertDialog alertDialog;

        switch(v.getId()){
            case R.id.colorButton:
                final int temp_red = _red;
                final int temp_green = _green;
                final int temp_blue = _blue;

                //builds alert dialog for color
                builder = new AlertDialog.Builder(this);

                // Get the layout inflater
                inflater = this.getLayoutInflater();

                //Button press sound
                button_sound.start();

                // Inflate the dialog_color.xml layout and create the View
                final View colorDialogView = inflater.inflate(R.layout.dialog_color, null);

                //Switch the fonts of the text fields
                ((TextView) colorDialogView.findViewById(R.id.textViewRed)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));
                ((TextView) colorDialogView.findViewById(R.id.textViewGreen)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));
                ((TextView) colorDialogView.findViewById(R.id.textViewBlue)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));
                ((TextView) colorDialogView.findViewById(R.id.colorSwatchTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));

                //Sets color swatch to selected path color
                TextView swatch = (TextView)colorDialogView.findViewById(R.id.colorSwatch);
                swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));

                // Get access to the seekbar on this dialog.
                SeekBar seekBar = (SeekBar)colorDialogView.findViewById(R.id.seekBarRed);

                // Set progress bar to current color value
                seekBar.setProgress(_red);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    //Updates red to value shown on seekbar
                    //Updates swatch in real time
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        _red = progress;
                        TextView swatch = (TextView)colorDialogView.findViewById(R.id.colorSwatch);
                        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {  }
                });

                // Get access to the seekbar on this dialog.
                seekBar = (SeekBar)colorDialogView.findViewById(R.id.seekBarGreen);

                // Set progress bar to current color value
                seekBar.setProgress(_green);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //Updates green to value shown on seekbar
                        //Updates swatch in real time
                        _green = progress;
                        TextView swatch = (TextView)colorDialogView.findViewById(R.id.colorSwatch);
                        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {  }
                });

                // Get access to the seekbar on this dialog.
                seekBar = (SeekBar)colorDialogView.findViewById(R.id.seekBarBlue);

                // Set progress bar to current color value
                seekBar.setProgress(_blue);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //Updates blue to value shown on seekbar
                        //Updates swatch in real time
                        _blue = progress;
                        TextView swatch = (TextView)colorDialogView.findViewById(R.id.colorSwatch);
                        swatch.setBackgroundColor(Color.rgb(_red,_green,_blue));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {  }
                });

                // Set custom view
                builder.setView(colorDialogView)
                        .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(this), getAssets(), "Color Picker"))
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Updates brush to correct color
                                mDesignView.setBrushColor(_red,_green,_blue);
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

                break;
            case R.id.clearButton:
                //builds alert dialog for clearing
                builder = new AlertDialog.Builder(this);

                // Get the layout inflater
                inflater = this.getLayoutInflater();

                //Button press sound
                button_sound.start();

                // Inflate the dialog_clear.xml layout and create the View
                final View clearDialogView = inflater.inflate(R.layout.dialog_clear, null);

                builder.setView(clearDialogView)
                        .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(this), getAssets(), "Clear Path"))
                        .setMessage("Do you want to clear your path?")
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                mDesignView.clear();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                alertDialog = builder.create();

                alertDialog.show();

                //Set message font
                ((TextView) alertDialog.findViewById(android.R.id.message)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));

                break;
            case R.id.saveButton:
                //Creates intent object to return when saving
                final Intent resultIntent = new Intent();

                //builds alert dialog for saving
                builder = new AlertDialog.Builder(this);

                // Get the layout inflater
                inflater = this.getLayoutInflater();

                //Button press sound
                button_sound.start();

                // Inflate the dialog_save.xml layout and create the View
                final View saveDialogView = inflater.inflate(R.layout.dialog_save, null);

                builder.setView(saveDialogView)
                        .setCustomTitle(FormatUtilities.makeDialogTitle(new TextView(this), getAssets(), "Save Your Design"))
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText)saveDialogView.findViewById(R.id.trace_name);
                                String path_name = name.getText().toString();
                                MainActivity.traces.put(path_name, mDesignView.getPath());
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                alertDialog = builder.create();
                alertDialog.show();

                //Set message text to proper font
                ((TextView) alertDialog.findViewById(R.id.trace_name)).setTypeface(Typeface.createFromAsset(getAssets(), "mvboli.ttf"));

                break;
            case R.id.mapToggleButton:
                // TODO - add/remove map to DesignView background

                break;
        }
    }
}
