package com.example.user.snakegame;

import android.graphics.RectF;

/**
 * Created by user on 21/08/2016.
 */
public class GameRules {


    public GameRules(){
    }

    public boolean checkWallCollision(Snake snake){
        //just for left and right
        RectF snakerect = snake.getRect();
        if (snakerect.left <= 0 ){
            return false;
        } else {return true;}

    }
}
