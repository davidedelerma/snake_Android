package com.example.user.snakegame;

/**
 * Created by user on 24/08/2016.
 */
public class User {
    int score;
    int record;
    public User (){
        this.score = 0;
        this.record = 0;
    }

    public void updateScore(){
        score++;
    }

    public int getScore() {
        return score;
    }

}
