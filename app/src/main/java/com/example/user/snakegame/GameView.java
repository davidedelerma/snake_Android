package com.example.user.snakegame;

import android.content.Context;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by user on 20/08/2016.
 */
public class GameView extends SurfaceView implements Runnable{

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playing;
    long fps;
    private long timeThisFrame;
    int screenX, screenY;
    Canvas canvas;
    Paint paint;
    GameRules gameRules;
    Board board;
    Snake snake;
    Fruit fruit;


    public GameView (Context context, int screenX, int screenY){
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        ourHolder = getHolder();
        paint = new Paint();
        board = new Board(20, 20, this.screenX-20, this.screenY - 200);
        fruit = new Fruit(board.getRect().bottom-15, board.getRect().top+15, board.getRect().right-15, board.getRect().left+15);
        snake = new Snake(board.getRect().centerX(), board.getRect().centerY());
        snake.update();
        //gameRules = new GameRules(snake);
    }

    @Override// why I should override from an interface?
    public void run(){
        while(playing){
            gameRules = new GameRules(snake);
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis()-startFrameTime;
            if (timeThisFrame > 0)
            {
                fps = 1000/timeThisFrame;
            }
        }
    }

    public void update(){
       // boolean check = gameRules.checkFruitCollision(board,fruit);
        if (gameRules.checkWallCollision(board) && gameRules.checkFruitCollision(board,fruit)) {
            snake.update();
        }
        else
        {
            if (ourHolder.getSurface().isValid()) {
                playing = false;
            }
//            Context myContext = this.getContext();
//            Intent newintent = new Intent();
//            newintent.setClass(myContext,GameOverActivity.class);
//            myContext.startActivity(newintent);
        }
    }

    public void draw(){
        if (ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawARGB(255, 255, 255, 255);
            paint.setColor(Color.argb(255, 255, 0, 255));
            canvas.drawRect(board.getRect(), board.getPaint());
            snake.draw(canvas);
            fruit.draw(canvas);
            paint.setTextSize(45);
            canvas.drawText("FPS:" + fps, 20, 40, paint);
            if (!playing){canvas.drawText("GAME OVER", 20, screenY-40, paint);};
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RectF head = snake.getSnakeBody().getLast();
                //if motion up or down
                if (snake.getMovementSate() == snake.UP || snake.getMovementSate() == snake.DOWN) {

                    if (motionEvent.getX() > head.centerX()) {
                        snake.setMovementState(snake.RIGHT);
                    } else if (motionEvent.getX() < head.centerX()) {
                        snake.setMovementState(snake.LEFT);
                    }
                } else if (snake.getMovementSate() == snake.LEFT || snake.getMovementSate() == snake.RIGHT) {

                    if (motionEvent.getY() < head.centerY()){
                        snake.setMovementState(snake.DOWN);
                     } else if (motionEvent.getY() > head.centerY()){
                        snake.setMovementState(snake.UP);
                    }
                }
                break;

        }
        return true;
    }
}


