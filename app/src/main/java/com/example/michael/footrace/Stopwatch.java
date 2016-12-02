package com.example.michael.footrace;

import android.content.Context;

/**
 * Stopwatch tracks timing for gameplay
 */
public class Stopwatch implements Runnable {

    Context mContext; // getsContext passed in from activity
    long mStartTime; // contains start time (0)
    boolean mIsRunning; // true if game is in progress

    /**
     * Constructor for the class for normal usage
     * @param context the Activity which is responsible fot this instance of class
     */
    public Stopwatch(Context context) {
        mContext = context;
        mStartTime = 0;
    }

    /**
     * Starts the Stopwatch
     */
    public void start() {
        mIsRunning = true;
    }

    /**
     * Stops the Stopwatch
     */
    public void stop() {
        mIsRunning = false;
    }

    @Override
    public void run() {
        while(mIsRunning) {

            //Elapsed time
            long e_time = System.currentTimeMillis() - mStartTime;

            //converts time into seconds and milliseconds
            int seconds = (int) (e_time / 1000) % 60;
            int millis = (int) e_time % 1000;

            //updates the timer text on the activity passed in
            ((PlayGame) mContext).updateTimerText(String.format("Time: %02d:%03d", seconds, millis));

            //Apparently this helps limit CPU usage
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}