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

    public boolean checkFruitCollision(Board board, Fruit fruit){
        head = this.body.getLast();
        //SAT (Separhating Axses Theorem) theorem
        float distX = Math.abs(head.centerX() - (float) fruit.getFoodObj().centerX());
        float halfWidthX = (head.width())/2 + (float) fruit.getFoodObj().width()/2;
        float distY = Math.abs(head.centerY() - (float) fruit.getFoodObj().centerY());
        float halfWidthY = Math.abs(head.height()/2 + (float) fruit.getFoodObj().height()/2);

        if(distX < halfWidthX && distY < halfWidthY )
        {
            // collision
           return true;
        }
        else {return false;}
    }

    public boolean checkCollisionWithItself(){
        head = this.body.getLast();
        for (int i= 0; i < body.size()-1; i++){
            float distX = Math.abs(head.centerX() - body.get(i).centerX());
            float halfWidthX = (head.width())/2 + body.get(i).width()/2;
            float distY = Math.abs(head.centerY() - body.get(i).centerY());
            float halfWidthY = Math.abs(head.height()/2 + body.get(i).height()/2);
            if(distX < halfWidthX && distY < halfWidthY )
            {
                // collision
                return true;
            }
        }
        return false;
    }
}
