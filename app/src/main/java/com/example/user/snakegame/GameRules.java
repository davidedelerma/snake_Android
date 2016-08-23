package com.example.user.snakegame;

import android.graphics.RectF;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by user on 21/08/2016.
 */
public class GameRules {

    private LinkedList<RectF> body;
    private RectF head;

    public GameRules(Snake snake){
        this.body = snake.getSnakeBody();
    }

    public boolean checkWallCollision(Board board){
        head = this.body.getLast();
        if (head.right <= board.getRect().left || head.right >= board.getRect().right){
            return false;
        }
        else if(head.top >= board.getRect().bottom || head.bottom <= board.getRect().top){
            return false;
      }
        else {return true;}

    }

//    public boolean checkFruitCollision(Board board){
//        head = this.body.getLast();
//        if (){
//           return false;
//        } else {return true;}
//    }
}
