package com.example.user.snakegame;

import android.graphics.RectF;
import android.util.Log;

/**
 * Created by user on 21/08/2016.
 */
public class Snake {

    private RectF rect;
    private float length;
    private float height;
    private float x;
    private float y;
    private float snakeSpeed;

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    //add up and down
    private int snakeMoving = STOPPED;
    int screenX;
    int screenY;


    public Snake(int screenX, int screenY){
        length = 20;
        height = 20;
        this.screenX = screenX;
        this.screenY = screenY;
        // Start snake in roughly the sceen centre
        x = screenX / 2;
        y = screenY / 2;
        rect = new RectF(x, y, x + length, y + height);
        // How fast is the paddle in pixels per second
        snakeSpeed = 350;
    }

    public RectF getRect(){
        return rect;
    }

    public void setMovementState(int state){
        snakeMoving = state;
    }

    public void update(long fps){
        if(snakeMoving == LEFT){
            x = x - snakeSpeed / fps;
        }

        if(snakeMoving == RIGHT){
            x = x + snakeSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;

        String rectright = Float.toString(rect.right);
        String rectleft = Float.toString(rect.left);
        String stringx = Integer.toString(this.screenX);
        Log.d("rect right + +rect left + screen X", rectright+" "+rectleft+" "+stringx);



    }


}
