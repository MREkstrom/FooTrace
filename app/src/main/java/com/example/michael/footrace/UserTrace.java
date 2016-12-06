package com.example.michael.footrace;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Dan on 12/5/2016.
 */
public class UserTrace {
    private Path _path;
    private Paint _paint;
    private float endX = 0;
    private float endY = 0;

    public UserTrace(){
        _path = new Path();
        _paint = new Paint();
    }

    public Path getPath() {
        return _path;
    }

    public Paint getPaint() {
        return _paint;
    }

    public float getEndX(){
        return endX;
    }

    public float getEndY(){
        return endY;
    }

    public void setAttributes(Path path, Paint paint, float x, float y){
        _path = path;
        _paint = paint;
        endX = x;
        endY = y;
    }
}
