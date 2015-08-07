package com.example.user.georgaphywar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 7.8.2015 г..
 */
public class DrawBitmap extends View{
    Bitmap map;
   public DrawBitmap(Context ctx,Bitmap map){
       super(ctx);
       this.map = map;
   }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(map,0,0,null);
    }
}
