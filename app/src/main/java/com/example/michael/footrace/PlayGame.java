package com.example.michael.footrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import static com.example.michael.footrace.PlayGame.Mode.MULTI;
import static com.example.michael.footrace.PlayGame.Mode.SINGLE;

public class PlayGame extends AppCompatActivity implements SensorEventListener{

    public enum Mode {SINGLE, MULTI};

    private WindowManager _windowManager;
    private Display _display;
    private GameView _gameView;
    private int _displayWidth, _displayHeight;

    private SensorManager _sensorManager;
    private Sensor _accel;

    private Mode _mode;

    private float[] _gravity = {0,0,0};
    private float[] _linearAcceleration = {0,0,0};

    private Stopwatch _timer; // Stopwatch timer
    private Thread _timerThread;//Thread for Stopwatch
    private TextView _timeDisplay; // Text for stopwatch

    // TODO - finish implementing gameplay and hook it up to the menus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        _accel = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        _sensorManager.registerListener(this, _accel, SensorManager.SENSOR_DELAY_GAME);

        // Old way of retrieving display w/h. Kept commented in case it's needed.
//        _windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        _display = _windowManager.getDefaultDisplay();
//
//        Point size = new Point();
//        _display.getSize(size);
//        _displayWidth = size.x/2;
//        _displayHeight = size.y/2;

        _gameView = (GameView) findViewById(R.id.gameView);

        _displayWidth = _gameView.getWidth();
        _displayHeight = _gameView.getHeight();

        Intent modeIntent = getIntent();
        String mode = modeIntent.getStringExtra("Mode");
        String pathName = modeIntent.getStringExtra("PathName");
        switch(mode){
            case "SP":
                _mode = SINGLE;
                break;
            case "MP":
                _mode = MULTI;
                break;
        } // MODES NOT USED YET

        // Set the design to be traced
        Path basePath = MainActivity.traces.get(pathName);
        _gameView.setBasePath(basePath);

        new AlertDialog.Builder(this)
                .setTitle("Press when ready!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        start();
                    }})
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                }).show();

        //Sets stopwatch textView
        _timeDisplay = (TextView) findViewById(R.id.stopwatch);

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        // Apply a high-pass filter to the motion to remove the effect of gravity.
        // Code stolen from official Android SensorEvent tutorial
        final float alpha = (float) 0.8;

        _gravity[0] = alpha * _gravity[0] + (1 - alpha) * event.values[0];
        _gravity[1] = alpha * _gravity[1] + (1 - alpha) * event.values[1];
        _gravity[2] = alpha * _gravity[2] + (1 - alpha) * event.values[2];

        _linearAcceleration[0] = event.values[0] - _gravity[0];
        _linearAcceleration[1] = event.values[1] - _gravity[1];
        _linearAcceleration[2] = event.values[2] - _gravity[2];

        //We only care about the X and Y components:
        // TODO- enforce holding phone parallel to ground for this to work properly? Calculation stripping?

        float xForce = _linearAcceleration[0], yForce = _linearAcceleration[1];

        _gameView.update(xForce, yForce);
    }

    // Begin playing
    public void start (){
        //create stopwatch, run it on new thread
        _timer = new Stopwatch(PlayGame.this);
        _timerThread = new Thread(_timer);
        _timerThread.start();

        //start the timer
        _timer.start();

    }

    // Complete game and return to menu, show score screen
    public void done (View v){
        // TODO- "calculate" / save score and return
        //stop timer
        _timer.stop();

        //stop the stopwatch thread
        _timerThread.interrupt();
        _timerThread = null;

        //Captures ending time, can be passed to results screen
        String endTime = _timeDisplay.getText().toString();


        Intent result = new Intent();
            //Put extras with score

        setResult(RESULT_OK, result);
        finish(); // possibly start new Activity for results, then return from that first? alert dialog?
    }

    @Override
    public void onAccuracyChanged(Sensor accel, int accuracy){
        //Do nothing (required override)
    }

    @Override
    public void onPause(){
        super.onPause();
        _sensorManager.unregisterListener(this); // Unregister sensor listener to avoid battery drain
    }

    @Override
    public void onResume(){
        super.onResume();
        _sensorManager.registerListener(this, _accel, SensorManager.SENSOR_DELAY_GAME); // Re-register on focus regain
    }

    /**
     * Update the text of the stopwatch
     * @param timeAsText updates stopwatch
     */
    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _timeDisplay.setText(timeAsText);
            }
        });
    }
}
