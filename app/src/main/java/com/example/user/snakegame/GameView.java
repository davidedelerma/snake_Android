package com.example.user.snakegame;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;
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
    int screenX, screenY, previouState, record;
    Canvas canvas;
    Paint paint;
    GameRules gameRules;
    Board board;
    Snake snake;
    Fruit fruit;
    User user;
    Button button;
    PlayButton play;
    Context context;


    public GameView (Context context, int screenX, int screenY){
        super(context);
        this.context = context;
        Resources res = getResources();
        this.screenX = screenX;
        this.screenY = screenY;
        ourHolder = getHolder();
        this.record = getRecord();
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
    }

    @Override// why I should override from an interface?
    public void run(){
        while(playing){
            gameRules = new GameRules(snake);
            update();
            draw();
        }
    }

    public void saveRecord( int score){
        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int oldScore = prefs.getInt("key", 0);
        if( score > oldScore ){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt("key", score);
            edit.commit();
        }
    }

    public int getRecord(){
        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        return prefs.getInt("key",0);
    }
//    private static final String PREF_SAVEDTEXT = "savedText";


//    public static void setStoredText(Context context, String text){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        //create the key value pair
//        editor.putString(PREF_SAVEDTEXT,text);
//        editor.apply();
//    }
//
//    public static String getStoredText(Context context){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String text = sharedPreferences.getString(PREF_SAVEDTEXT,null);
//        return text;
//    }

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
                user.setRecord(user.getScore());
                saveRecord(user.getRecord());
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
            canvas.drawLine(board.getRect().left, board.getRect().centerY(), board.getRect().right, board.getRect().centerY(), paint);
            canvas.drawLine(board.getRect().centerX(), board.getRect().top, board.getRect().centerX(), board.getRect().bottom, paint);
            canvas.drawText("Score:" + Integer.toString(user.getScore()), 20, board.getRect().bottom + 60, paint);
            canvas.drawText("Record:"+Integer.toString(record), 20, board.getRect().bottom+110, paint);
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

                    if (motionEvent.getX() > board.getRect().centerX()
                            && board.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY() ) ) {
                        snake.setMovementState(snake.RIGHT);
                    } else if (motionEvent.getX() < board.getRect().centerX()
                            && board.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY()) ) {
                        snake.setMovementState(snake.LEFT);
                    }
                } else if (snake.getMovementSate() == snake.LEFT || snake.getMovementSate() == snake.RIGHT) {

                    if (motionEvent.getY() < board.getRect().centerY()
                            && board.getRect().contains( (int) motionEvent.getX(),(int) motionEvent.getY() ) ){
                        snake.setMovementState(snake.DOWN);
                    } else if (motionEvent.getY() > board.getRect().centerY()
                            && board.getRect().contains( (int) motionEvent.getX(),(int) motionEvent.getY() ) ){
                        snake.setMovementState(snake.UP);
                    }
                }
                break;

        }
        return true;
    }
}


