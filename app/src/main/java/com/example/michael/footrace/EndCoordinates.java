package com.example.michael.footrace;

/**
 * Created by Dan on 12/5/2016.
 */
public class EndCoordinates {
    private float endX = 0;
    private float endY = 0;

    public EndCoordinates(float x, float y){
        endX = x;
        endY = y;
    }

    public float getEndX(){
        return endX;
    }

    public float getEndY(){
        return endY;
    }

    public void setCoords(float x, float y){
        endX = x;
        endY = y;
    }
}
