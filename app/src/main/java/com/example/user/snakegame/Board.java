package com.example.user.snakegame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by user on 21/08/2016.
 */
public class Board {
    private int left, top, right, bottom;
    private Rect rect;
    private Paint myPaint;
    public Board(int left, int top, int right, int bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.myPaint = myPaint;
        this.bottom = bottom;
        rect = new Rect(left, top, right, bottom);
        this.myPaint = new Paint();
        this.myPaint.setColor(Color.rgb(8, 198, 246));
        this.myPaint.setStrokeWidth(10);
        this.myPaint.setStyle(Paint.Style.STROKE);
    }

    public Rect getRect(){
        return rect;
    }

    public Paint getPaint(){
        return myPaint;
    }
}
