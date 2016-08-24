package com.example.user.snakegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by user on 24/08/2016.
 */
public class PlayButton {
    Bitmap bitmap;
    int x, y;
    Rect rect;
    public PlayButton(Resources res, int x, int y){
        this.bitmap = BitmapFactory.decodeResource(res, R.drawable.play);

        this.x = x;
        this.y = y;
        this.rect= new Rect (x, y, x+120, y+120);
    }

    public Rect getRect(){
        return rect;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.rgb(8, 198, 246));
        canvas.drawBitmap(this.bitmap,null,rect,null);

    }
}
