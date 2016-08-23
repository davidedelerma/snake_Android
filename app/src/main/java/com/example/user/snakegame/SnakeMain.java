package com.example.user.snakegame;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;


/**
 * Created by user on 19/08/2016.
 */
public class SnakeMain extends AppCompatActivity {

    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenX = size.x;
        int screenY = size.y;
        gameView = new GameView(this, screenX, screenY);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        // Tell the gameView pause method to execute
        gameView.pause();
    }
}
