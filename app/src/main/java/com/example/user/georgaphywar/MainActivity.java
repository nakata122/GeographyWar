package com.example.user.georgaphywar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //mode[0] drag  mode[1] zoom
    boolean[] mode={true,false};
    boolean scale = false, br = false, first = true, isFirstClosed = false;
    RelativeLayout screen;
    ImageView europe;
    ScaleGestureDetector detect;
    TextView text;
    float scaleFactor=1.8f,lastFactor=1.8f;
    Bitmap bitmap,map;
    Dialog level;
    Checking check;
    String col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.europe_map);
        text = (TextView) findViewById(R.id.textView);
        screen = (RelativeLayout) findViewById(R.id.rel);
        europe = (ImageView) findViewById(R.id.europe);
        europe.setScaleX(scaleFactor);
        europe.setScaleY(scaleFactor);
        check = new Checking();
        //the dialog
            level = new Dialog(this);
            level.setTitle("Choose your difficulty.");
            level.setContentView(R.layout.popup);
            level.setCancelable(false);
            level.setCanceledOnTouchOutside(false);
            Button easy = (Button) level.findViewById(R.id.easy);
            easy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFirstClosed = true;
                    level.dismiss();
                }
            });
            Button medium = (Button) level.findViewById(R.id.medium);
            medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    level.dismiss();
                    isFirstClosed = true;
                }
            });
            Button hard = (Button) level.findViewById(R.id.hard);
            hard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    level.dismiss();
                    isFirstClosed = true;
                }
            });
            level.show();

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFirstClosed) {
                    alertDialog();
                }
                if(!isFirstClosed)
                    h.postDelayed(this,500);
            }
        }, 10);



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
                scaleFactor = Math.max(1.8f, Math.min(scaleFactor, 6.0f));
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
                        if(first)
                            map = Bitmap.createScaledBitmap(bitmap, (int) (europe.getWidth()*(scaleFactor/1.8)), (int) (europe.getHeight()*(scaleFactor)),true);
                        first = false;
                        mode[0] = false;
                        mode[1] = false;
                        currentX = (int) event.getX();
                        currentY = (int) event.getY();
                        if(br) {
//                            DrawBitmap bit = new DrawBitmap(getApplicationContext(),map);
//                            screen.addView(bit);
                            ImageView pin = new ImageView(MainActivity.this);
                            pin.setImageResource(R.drawable.albania);
                            screen.addView(pin);
                            float outX = (europe.getWidth()*(scaleFactor/1.8f) - europe.getWidth())/2;
                            float outY = (europe.getHeight()*(scaleFactor) - europe.getHeight())/2;
                            int color = map.getPixel((int) (event.getX() + (outX - europe.getX())), (int) (event.getY() + (outY - europe.getY())));
                            col = String.valueOf(Integer.toHexString(color));
                            col = col.substring(2);
                            if(col.equals(check.getColor())){
                                alertDialog();
                            }
                            text.setTextColor(color);
                            text.setText(String.valueOf((event.getX() + (outX - europe.getX()))/scaleFactor) + "   " + String.valueOf((event.getY() + (outY - europe.getY()))/scaleFactor));
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        mode[0] = true;
                        if (mode[1]) detect.onTouchEvent(event);
                        if (mode[0]) {
                            x = (int) event.getX(0);
                            y = (int) event.getY(0);
                            checkX = ((europe.getWidth()) * (scaleFactor/1.8) - europe.getWidth()) / 2;
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
                    case MotionEvent.ACTION_POINTER_UP:
                    map = Bitmap.createScaledBitmap(bitmap, (int) (europe.getWidth()*(scaleFactor/1.8)), (int) (europe.getHeight()*(scaleFactor)),true);
                    break;
                    case MotionEvent.ACTION_UP:
                        scale = false;
                        if(!mode[1]) br = true;
                        else br = false;
                        break;
                }

                return true;
            }
        });
    }

    private void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("You are looking for: " + check.getRandom());
        builder.create();
        final AlertDialog alertdialog = builder.create();
        alertdialog.show();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                alertdialog.dismiss();
            }
        }.start();
    }
}
