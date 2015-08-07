package com.example.user.georgaphywar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //mode[0] drag  mode[1] zoom
    boolean[] mode={true,false};
    boolean scale = false, br = false, timerEnd = true;
    RelativeLayout screen;
    ImageView europe;
    ScaleGestureDetector detect;
    TextView text;
    float scaleFactor=1.5f,lastFactor=1.5f;
    Bitmap bitmap,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.europe_small);
        text = (TextView) findViewById(R.id.textView);
        screen = (RelativeLayout) findViewById(R.id.rel);
        europe = (ImageView) findViewById(R.id.europe);
        europe.setScaleX(scaleFactor);
        europe.setScaleY(scaleFactor);
        detect = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (!scale) {
                    if (detector.getScaleFactor() >= 1) {
                        scaleFactor *= detector.getScaleFactor() * 1.03;
                    } else {
                        scaleFactor *= detector.getScaleFactor();
                    }
                } else {
                    scale = false;
                }
                scaleFactor = Math.max(1.5f, Math.min(scaleFactor, 6.0f));
                europe.setScaleX(scaleFactor);
                europe.setScaleY(scaleFactor);
                if (scaleFactor < lastFactor) {
                    europe.setX(europe.getX()/scaleFactor);
                    europe.setY(europe.getY()/scaleFactor);
                }
                lastFactor = scaleFactor;
                return true;
            }
        });
        screen.setOnTouchListener(new View.OnTouchListener() {
            int currentX, currentY, x, y;
            double checkX, checkY;

            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction() & event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mode[0] = false;
                        mode[1] = false;
                        currentX = (int) event.getX();
                        currentY = (int) event.getY();
                        if(br) {
                            map = Bitmap.createScaledBitmap(bitmap, (int) (europe.getWidth()*(scaleFactor/1.5)), (int) (europe.getHeight()*(scaleFactor)),true);
//                            DrawBitmap bit = new DrawBitmap(getApplicationContext(),map);
//                            screen.addView(bit);
                            float outX = (europe.getWidth()*(scaleFactor/1.5f) - europe.getWidth())/2;
                            float outY = (europe.getHeight()*(scaleFactor) - europe.getHeight())/2;
                            int color = map.getPixel((int) (event.getX() + (outX - europe.getX())), (int) (event.getY() + (outY - europe.getY())));
                            text.setText(String.valueOf(Integer.toHexString(color)));
                            text.setTextColor(color);
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        mode[0] = true;
                        if (mode[1]) detect.onTouchEvent(event);
                        if (mode[0]) {
                            x = (int) event.getX(0);
                            y = (int) event.getY(0);
                            checkX = ((europe.getWidth() - 275) * scaleFactor - europe.getWidth()) / 2;
                            checkY = ((europe.getHeight() - 10) * scaleFactor - europe.getHeight()) / 2;
                            if (europe.getX() < 0 + checkX && (europe.getX() * -1) < checkX) {
                                europe.setX(europe.getX() - (currentX - x));
                            } else if ((currentX - x >= 0 && europe.getX() >= 0) || (currentX - x <= 0 && (europe.getX() * -1) >= checkX)) {
                                europe.setX(europe.getX() - (currentX - x));
                            }

                            if (europe.getY() < 0 + checkY && (europe.getY() * -1) < checkY) {
                                europe.setY(europe.getY() - (currentY - y));
                            } else if ((currentY - y >= 0 && europe.getY() >= 0) || (currentY - y <= 0 && (europe.getY() * -1) >= checkY)) {
                                europe.setY(europe.getY() - (currentY - y));
                            }
                            currentX = x;
                            currentY = y;
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        scale = true;
                        mode[1] = true;
                        mode[0] = false;
                        break;
//                    case MotionEvent.ACTION_POINTER_UP:
//                        map = Bitmap.createScaledBitmap(bitmap, (int) (europe.getWidth() * scaleFactor), (int) (europe.getHeight() * scaleFactor),true);
//                        break;
                    case MotionEvent.ACTION_UP:
                        scale = false;
                        if(!mode[1] && !mode[0]) br = true;
                        else br = true;
                        break;
                }

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
