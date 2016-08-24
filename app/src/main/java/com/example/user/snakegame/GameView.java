package com.example.user.snakegame;

import android.content.Context;
import android.content.Intent;

import android.content.res.Resources;
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
    int screenX, screenY, previouState;
    Canvas canvas;
    Paint paint;
    GameRules gameRules;
    Board board;
    Snake snake;
    Fruit fruit;
    User user;
    Button button;
    PlayButton play;


    public GameView (Context context, int screenX, int screenY){
        super(context);
        Resources res = getResources();
        this.screenX = screenX;
        this.screenY = screenY;
        ourHolder = getHolder();
        user = new User();
        paint = new Paint();
        //for bigger devices
        if (screenX > 750 && screenY > 800){
            board = new Board(20, 20, 780, 820);
        } else {
            board = new Board(20, 20, 500, 720);
        }
        play = new PlayButton(res, board.getRect().right-240,board.getRect().bottom+20);
        button = new Button(res, board.getRect().right-120,board.getRect().bottom+20);
        fruit = new Fruit(board.getRect(), 40);
        snake = new Snake(40, 40, 40);
        snake.update();
        this.previouState = previouState;
        //gameRules = new GameRules(snake);
    }

    @Override// why I should override from an interface?
    public void run(){
        while(playing){
            gameRules = new GameRules(snake);
            update();
            draw();
        }
    }

    public void refreshGame(){
        Context myContext = this.getContext();
        Intent newintent = new Intent();
        newintent.setClass(myContext,SnakeMain.class);
        myContext.startActivity(newintent);

    }

    public void update(){
        if (gameRules.checkWallCollision(board)
                && !gameRules.checkCollisionWithItself()
                && !gameRules.checkFruitCollision(board,fruit)) {
            snake.update();
        }
        else if (gameRules.checkWallCollision(board)
                && !gameRules.checkCollisionWithItself()
                && gameRules.checkFruitCollision(board,fruit))
        {

            snake.grow();
            fruit = new Fruit(board.getRect(), 40);
            user.updateScore();
        }
        else
        {
            if (ourHolder.getSurface().isValid()) {
                playing = false;
            }
//
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
            button.draw(canvas);
            play.draw(canvas);
            paint.setTextSize(50);
            canvas.drawText("score:"+Integer.toString(user.getScore()), 20, board.getRect().bottom+60, paint);
            if (!playing){canvas.drawText("GAME OVER", board.getRect().centerX()-150, board.getRect().centerY(), paint);};
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
                if (button.getRect().contains((int) motionEvent.getX(),(int) motionEvent.getY())){
                    if(snake.getMovementSate() != snake.STOPPED){
                        previouState = snake.getMovementSate();
                        snake.setMovementState(snake.STOPPED);
                    } else {
                        snake.setMovementState(previouState);
                    }

                }

                if (play.getRect().contains((int) motionEvent.getX(),(int) motionEvent.getY())){
                    refreshGame();
                }


                if (snake.getMovementSate() == snake.UP || snake.getMovementSate() == snake.DOWN) {

                    if (motionEvent.getX() > head.centerX()
                            && board.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY() ) ) {
                        snake.setMovementState(snake.RIGHT);
                    } else if (motionEvent.getX() < head.centerX()
                            && board.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY()) ) {
                        snake.setMovementState(snake.LEFT);
                    }
                } else if (snake.getMovementSate() == snake.LEFT || snake.getMovementSate() == snake.RIGHT) {

                    if (motionEvent.getY() < head.centerY()
                            && board.getRect().contains( (int) motionEvent.getX(),(int) motionEvent.getY() ) ){
                        snake.setMovementState(snake.DOWN);
                     } else if (motionEvent.getY() > head.centerY()
                            && board.getRect().contains( (int) motionEvent.getX(),(int) motionEvent.getY() ) ){
                        snake.setMovementState(snake.UP);
                    }
                }
                break;

        }
        return true;
    }
}


