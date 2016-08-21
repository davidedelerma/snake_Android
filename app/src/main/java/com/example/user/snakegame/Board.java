package com.example.user.snakegame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by user on 21/08/2016.
 */
public class Board {
    private float left, top, right, bottom;
    private RectF rectF;
    private Paint myPaint;
    public Board(float left, float top, float right, float bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.myPaint = myPaint;
        this.bottom = bottom;
        rectF = new RectF(left, top, right, bottom);
        this.myPaint = new Paint();
        this.myPaint.setColor(Color.rgb(8, 198, 246));
        this.myPaint.setStrokeWidth(10);
        this.myPaint.setStyle(Paint.Style.STROKE);
    }

    public RectF getRectF(){
        return rectF;
    }

    public Paint getPaint(){
        return myPaint;
    }
}
