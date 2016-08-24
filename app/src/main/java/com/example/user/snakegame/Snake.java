package com.example.user.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by user on 21/08/2016.
 */
public class Snake {

//    private String direction;
    private LinkedList<RectF> body;
    private RectF head, tail;
    private float X, Y, DX, DY, width, halfWidth , drawWidth;
    private float snakeSpeed;
    private Paint myPaint;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;
    //add up and down
    private int snakeMoving = RIGHT;
    private long timeSinceUpdate;

// initial position instead than rect in the contructor
    public Snake(float X, float Y , float width){
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.width = width;
        this.DX = this.width;
        this.DY = this.width;
        this.halfWidth = this.width/2;
        this.drawWidth = this.halfWidth-2;
        this.head = new RectF(X-this.halfWidth,Y+this.halfWidth,X+this.halfWidth,Y-this.halfWidth);
        this.tail = new RectF(X-this.DX-this.halfWidth,Y+this.halfWidth,X-this.DX+this.halfWidth,Y-this.halfWidth);
        this.body = new LinkedList<>();
        this.body.add(this.tail);
        this.body.add(this.head);
        this.myPaint = new Paint();
        this.myPaint.setColor(Color.rgb(10, 201, 99));
        // How fast is the snake in pixels per second
        this.snakeSpeed = 300;
    }

    public void setMovementState(int state){
        snakeMoving = state;
    }

    public int getMovementSate(){
        return snakeMoving;
    }

    public LinkedList<RectF> getSnakeBody(){
        return body;
    }

    public void update(){
        long elapsed = (System.nanoTime() - this.timeSinceUpdate) / 1000000;
        if (elapsed > snakeSpeed) {

            switch (snakeMoving){
                case RIGHT:
                    this.body.removeFirst();
                    this.X += this.DX;
                    this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                    this.body.add(this.head);
                    break;
                case LEFT:
                    this.body.removeFirst();
                    this.X -= this.DX;
                    this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                    this.body.add(this.head);
                    break;
                case UP:
                    this.body.removeFirst();
                    this.Y += this.DY;
                    this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                    this.body.add(this.head);
                    break;
                case DOWN:
                    this.body.removeFirst();
                    this.Y -= this.DY;
                    this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                    this.body.add(this.head);
                    break;
                case STOPPED:

                    break;
            }
            this.timeSinceUpdate=System.nanoTime();
        }
    }

    public void grow(){
       // long elapsed = (System.nanoTime() - this.timeSinceUpdate) / 1000000;
       // if (elapsed > snakeSpeed) {
        snakeSpeed -= 30;// snakeSpeed * (20/100);
        switch (snakeMoving){
            case RIGHT:
                this.X += this.DX;
                this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                this.body.add(this.head);
                break;
            case LEFT:
                this.X -= this.DX;
                this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                this.body.add(this.head);
                break;
            case UP:
                this.Y += this.DY;
                this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                this.body.add(this.head);
                break;
            case DOWN:
                this.Y -= this.DY;
                this.head = new RectF(X - drawWidth, Y + drawWidth, X + drawWidth, Y - drawWidth);
                this.body.add(this.head);
                break;
        }

    }

    public void draw(Canvas canvas){
        Paint headPaint = new Paint();
        headPaint.setColor(Color.rgb(255, 31, 31));
        canvas.drawRect(this.body.get(body.size()-1), headPaint);
        for(int i = 0; i < this.body.size()-1 ; i++){
            canvas.drawRect(this.body.get(i), this.myPaint);
        }
    }
}
