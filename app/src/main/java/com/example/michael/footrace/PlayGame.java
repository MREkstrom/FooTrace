package com.example.michael.footrace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.michael.footrace.MainActivity.button_sound;
import static com.example.michael.footrace.PlayGame.Mode.MULTI;
import static com.example.michael.footrace.PlayGame.Mode.SINGLE;

public class PlayGame extends AppCompatActivity implements SensorEventListener{

    public enum Mode {SINGLE, MULTI};

    AnimatedView animatedView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();

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

    private boolean _gameStarted = false;
    private String _pathName;

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
        _pathName = modeIntent.getStringExtra("PathName");
        switch(mode){
            case "SP":
                _mode = SINGLE;
                break;
            case "MP":
                _mode = MULTI;
                break;
        } // MODES NOT USED YET

        // Set the design to be traced
        UserTrace usrTrc = MainActivity.userTraces.get(_pathName);

        //Initializes path and paint objects
        _gameView.setBasePath(usrTrc.getPath(), _pathName);
        _gameView.setBasePaint(usrTrc.getPaint());

        //Sets stopwatch textView
        _timeDisplay = (TextView) findViewById(R.id.stopwatch);

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        if(!_gameStarted){ //Do not run sensor updates until game begins
            return;
        }
        // Apply a high-pass filter to the motion to remove the effect of gravity.
        // Code stolen from official Android SensorEvent tutorial
        final float alpha = (float) 1.0;

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
    public void startAndStop (View v){

        button_sound.start();

        if(_gameStarted == false){
            start();

            Button startStopButton = (Button)findViewById(R.id.startAndStopButton);
            startStopButton.setText("Done");
            _gameStarted = true;
        } else {
            //stop timer
            _timer.stop();

            //stop the stopwatch thread
            _timerThread.interrupt();
            _timerThread = null;

            //Captures ending time, can be passed to results screen
            String endTime = _timeDisplay.getText().toString();
            endTime = endTime.substring(6, endTime.length());


            Intent result = new Intent();
            switch(_mode){
                case SINGLE:
                    result = new Intent(this, Results.class);
                    break;
                case MULTI:
                    result = new Intent(this, MultiResults.class);
                    break;
            }

            result.putExtra("time_results", endTime);
            //Put extras with score and path name, for retry
            result.putExtra("PathName", _pathName);

            setResult(RESULT_OK, result);
            startActivity(result);
            finish();
        }
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

    public class AnimatedView extends ImageView {

        static final int width = 50;
        static final int height = 50;

        public AnimatedView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xffffAC23);
            mDrawable.setBounds((int) _gameView._startX,(int) _gameView._startY,(int) _gameView._startX + width, (int)_gameView._startY + height);

        }

        @Override
        protected void onDraw(Canvas canvas) {

            mDrawable.setBounds((int) _gameView._startX,(int) _gameView._startY,(int) _gameView._startX + width, (int)_gameView._startY + height);
            mDrawable.draw(canvas);
            invalidate();
        }
    }
}
