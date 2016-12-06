package com.example.michael.footrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {

    private Path _basePath = new Path(); // The background design to trace
    private Path _tracePath = new Path(); // The user's path created by motion

    private Paint _basePaint = new Paint();
    private Paint _tracePaint = new Paint();

    private PathMeasure _pathMeasure;

    private float _startX, _startY, _curX, _curY, _endX, _endY;

    private float _radius = 15;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyleAttr){
        _basePaint.setColor(Color.GRAY);
        _basePaint.setStyle(Paint.Style.STROKE);
        _basePaint.setStrokeWidth(12);
        _basePaint.setAntiAlias(true);
        _basePaint.setStrokeCap(Paint.Cap.ROUND);
        _basePaint.setStrokeJoin(Paint.Join.ROUND);

        _tracePaint.setColor(MainActivity.prof.get_color());
        _tracePaint.setStyle(Paint.Style.STROKE);
        _tracePaint.setStrokeWidth(10);
        _tracePaint.setAntiAlias(true);
        _tracePaint.setStrokeCap(Paint.Cap.ROUND);
        _tracePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void pathInit(String pathName){
        // Find starting point of the base path to use for the trace path
        float[] coords = {0f,0f};
        _pathMeasure = new PathMeasure(_basePath, false);
        _pathMeasure.getPosTan(0, coords, null);
        _startX = coords[0];
        _startY = coords[1];

        // Initialize the starting path
        _tracePath.moveTo(_startX,_startY);
        _curX = _startX;
        _curY = _startY;

        _pathMeasure.getPosTan(_pathMeasure.getLength(), coords, null);
        _endX = MainActivity.userTraces.get(pathName).getEndX();
        _endY = MainActivity.userTraces.get(pathName).getEndY();

        // Add circles marking start and end
        _basePath.addCircle(_startX,_startY,_radius, Path.Direction.CW);
        _basePath.addCircle(_endX,_endY,_radius,Path.Direction.CW);
    }

    public void setBasePath(Path path, String pathName){
        _basePath = path;
        pathInit(pathName);
        invalidate();
    }

    public void setBasePaint(Paint paint){
        if(paint.getColor() == _tracePaint.getColor()){
            _basePaint.setColor(Color.GRAY);
        } else {
            _basePaint = paint;
        }

        invalidate();
    }

    public void update(float xForce, float yForce){

        _curX += xForce; // X increases L->R in force axes and in display
        _curY -= yForce; // Y increases down->up in axis, up-down in display

        _tracePath.lineTo(_curX,_curY);
        invalidate();
    }

    public void setBaseColor(int red, int green, int blue){
        _basePaint.setColor(Color.rgb(red,green,blue));
        invalidate();
    }

    public void setTraceColor(int red, int green, int blue){
        _tracePaint.setColor(Color.rgb(red,green,blue));
        invalidate();
    }

    // Can be used to determine when the game ends
    // if we don't do a "Done" button
    public boolean inFinishZone(){
        Rect finishZone = new Rect((int) (_endX - _radius), (int)(_endY - _radius),
                                    (int)(_endX + _radius), (int)(_endY + _radius));

        return(finishZone.contains((int)_curX, (int)_curY));
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(_basePath,_basePaint);
        canvas.drawPath(_tracePath,_tracePaint);
    }
}
