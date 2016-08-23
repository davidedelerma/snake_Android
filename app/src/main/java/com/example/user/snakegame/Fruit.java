package com.example.user.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by user on 23/08/2016.
 */
public class Fruit {

    int X ,Y, minX, minY, maxX, maxY;
    Rect rect;
    Rect foodobj;
    int width, drawWidth;

    public Fruit(Rect rect, int width){
        this.width = width;
        this.rect = rect;
        this.drawWidth = this.width-2;
        this.minX = this.rect.left + this.width;
        this.maxX = this.rect.right - this.width;
        this.minY = this.rect.top + this.width;
        this.maxY = this.rect.bottom - this.width;
        Random rX = new Random();
        this.X = rX.nextInt((maxX - minX) + 1) + minX;
        Random rY = new Random();
        this.Y = rY.nextInt((maxY - minY) + 1) + minY;
        this.foodobj = new Rect(this.X-this.drawWidth,this.Y+this.drawWidth,this.X+this.drawWidth,this.Y-this.drawWidth);

    }

    public Rect getFoodObj(){
        return foodobj;
    }

    public void draw(Canvas canvas){
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.rgb(255, 238, 88));
        canvas.drawRect(foodobj, rectPaint);
    }

    public void undraw(Canvas canvas){
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(foodobj, rectPaint);
    }
}
