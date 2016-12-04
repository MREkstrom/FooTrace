package com.example.michael.footrace;

import android.content.Context;

/* This class is used as the main timer for gameplay*/
public class Stopwatch implements Runnable {

    Context mContext; // getsContext passed in from activity
    long mStartTime; // contains start time (0)
    boolean mIsRunning; // true if game is in progress

    public Stopwatch(Context context) {
        mContext = context;
        mStartTime = 0;
    }

    /*Start stopwatch*/
    public void start() {
        mIsRunning = true;
    }

    /*Stop the stopwatch*/
    public void stop() {
        mIsRunning = false;
    }

    /*thread which runs and updates stopwatch*/
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