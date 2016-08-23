package com.example.user.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by user on 23/08/2016.
 */
public class Fruit {

    int X;
    int Y;
    public Fruit(int maxY, int minY, int maxX, int minX){
        Random rX = new Random();
        this.X = rX.nextInt((maxX - minX) + 1) + minX;
        Random rY = new Random();
        this.Y = rY.nextInt((maxY - minY) + 1) + minY;

    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public void draw(Canvas canvas){
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.rgb(255, 238, 88));
        canvas.drawCircle(X, Y, 15, circlePaint);
    }

    public void undraw(Canvas canvas){
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.rgb(255, 255, 255));
        canvas.drawCircle(X, Y, 15, circlePaint);
    }
}
