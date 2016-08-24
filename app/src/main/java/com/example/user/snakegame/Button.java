package com.example.user.snakegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;

/**
 * Created by user on 24/08/2016.
 */
public class Button {
    Bitmap bitmap;
    int x, y;
    Rect rect;
    public Button(Resources res, int x, int y){
       this.bitmap = BitmapFactory.decodeResource(res, R.drawable.stop_new);

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
