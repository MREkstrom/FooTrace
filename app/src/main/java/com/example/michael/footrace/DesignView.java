package com.example.michael.footrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DesignView extends View {

    /*Initialize member variables*/
    private Path _path = new Path();
    private Paint _paint = new Paint();
    private float _prevPoint = -1;

    public DesignView(Context context) {
        super(context);
        init(null, 0);
    }

    public DesignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DesignView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /*Set up default paint object*/
    private void init(AttributeSet attrs, int defStyleAttr){
        _paint.setColor(Color.BLACK);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setStrokeWidth(12);
        _paint.setAntiAlias(true);
        _paint.setStrokeCap(Paint.Cap.ROUND);
        _paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /*returns path*/
    public Path getPath(){
        return _path;
    }

    /*changes brush color*/
    public void setBrushColor(int red, int green, int blue){
        _paint.setColor(Color.rgb(red,green,blue));
        invalidate();
    }

    /*Clears path*/
    public void clear() {
        _path.reset();
        _prevPoint = -1;
        invalidate();
    }

    /*Draws path to canvas*/
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(_path,_paint);
    }

    /*determines actions when user touches screen*/
    public boolean onTouchEvent(MotionEvent motionEvent){
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                /*connects lines for user if they are disconnected
                * as you cannot trace disconnected lines*/
                if(_prevPoint != -1){
                    _path.lineTo(touchX,touchY);
                }
                _path.moveTo(touchX,touchY);
            case MotionEvent.ACTION_MOVE:
                _path.lineTo(touchX,touchY);
            case MotionEvent.ACTION_UP:
                _path.moveTo(touchX,touchY);
                _prevPoint = touchX;
        }

        invalidate();
        return true;
    }

}
