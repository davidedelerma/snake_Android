package com.example.user.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    Canvas canvas;
    Paint paint;
    long fps;
    private long timeThisFrame;
    Bitmap bitmapBob;
    boolean isMoving = false;
    float walkSpeedPerSecond = 150;
    float bobXPosition= 10;
    Snake snake;
    int screenX;
    int screenY;

    public GameView (Context context, int screenX, int screenY){
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        ourHolder = getHolder();
        paint = new Paint();
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
        snake = new Snake(this.screenX ,this.screenY);
        snake.update(fps);
    }

    @Override// why I should override from an interface?
    public void run(){
        while(playing){
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
        snake.update(fps);
        if (isMoving){
            bobXPosition = bobXPosition + (walkSpeedPerSecond/fps);
        }
    }

    public void draw(){
        if (ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawARGB(255, 26, 128, 182);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(snake.getRect(), paint);
            // Make the text a bit bigger
            paint.setTextSize(45);
            // Display the current fps on the screen
            canvas.drawText("FPS:" + fps, 20, 40, paint);
            // Draw bob at bobXPosition, 200 pixels
            canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);
            // Draw everything to the screen
            // and unlock the drawing surface
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
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                if(motionEvent.getX() > screenX / 2){
                    snake.setMovementState(snake.RIGHT);
                }
                else{
                    snake.setMovementState(snake.LEFT);
                }
                break;
            case MotionEvent.ACTION_UP:
                isMoving = false;
                snake.setMovementState(snake.STOPPED);
                break;

        }
        return true;
    }
}


