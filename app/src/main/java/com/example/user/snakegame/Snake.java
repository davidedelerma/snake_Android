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
    private float X, Y, DX, DY;
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
    public Snake(float X, float Y){
        this.X = X;
        this.Y = Y;
        this.DX = 40;
        this.DY = 40;
        this.head = new RectF(X-18,Y+18,X+18,Y-18);
        this.tail = new RectF(X-this.DX-18,Y+18,X-this.DX+18,Y-18);
        this.body = new LinkedList<>();
        body.add(this.tail);
        body.add(this.head);
        this.myPaint = new Paint();
        this.myPaint.setColor(Color.rgb(10, 201, 99));
        // How fast is the snake in pixels per second
        this.snakeSpeed = 500;
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
                    body.removeFirst();
                    this.X += this.DX;
                    this.head = new RectF(X - 18, Y + 18, X + 18, Y - 18);
                    body.add(this.head);
                    break;
                case LEFT:
                    body.removeFirst();
                    this.X -= this.DX;
                    this.head = new RectF(X - 18, Y + 18, X + 18, Y - 18);
                    body.add(this.head);
                    break;
                case UP:
                    body.removeFirst();
                    this.Y += this.DY;
                    this.head = new RectF(X - 18, Y + 18, X + 18, Y - 18);
                    body.add(this.head);
                    break;
                case DOWN:
                    body.removeFirst();
                    this.Y -= this.DY;
                    this.head = new RectF(X - 18, Y + 18, X + 18, Y - 18);
                    body.add(this.head);
                    break;
//                default:
//                    //case right
//                    //if I press something case LEFT whatevere
//                    body.removeFirst();
//                        this.X += this.DX;
//                    this.head = new RectF(X - 18, Y + 18, X + 18, Y - 18);
//                    body.add(this.head);
            }
            this.timeSinceUpdate=System.nanoTime();
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
