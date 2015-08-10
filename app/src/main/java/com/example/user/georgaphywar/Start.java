package com.example.user.georgaphywar;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class Start extends AppCompatActivity {

    VideoView video;
    boolean moving = false,touching = false,movingAtAll=false;
    float startX;
    int mode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        video = (VideoView) findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.creationnew));
        video.start();

        new CountDownTimer(1900, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.startloop));
                video.start();
            }
        }.start();


        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mode == 0 || mode == 1 && !moving) video.start();
                h.postDelayed(this, 5000);
            }
        }, 10);
        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v,final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        movingAtAll = false;
                        touching = false;
                        new CountDownTimer(600, 1000) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                if(touching || !movingAtAll) {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);

                                }
                            }
                        }.start();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        movingAtAll = true;
                        if(startX-event.getX()>=-2 && startX-event.getX()<=2) touching = true;
                        if(mode == 0 && startX-event.getX()>=70){
                            touching = false;
                            moving = true;
                            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.transition_right_to_multyplayer));
                            video.start();
                            new CountDownTimer(2400, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.creationmultyplayer));
                                    video.start();
                                    mode = 1;
                                }
                            }.start();
                            new CountDownTimer(4000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    moving = false;
                                    video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.multyplayerloop));
                                    video.start();
                                }
                            }.start();

                        }
                        if(mode == 1 && startX-event.getX()<=0){
                            touching = false;
                            moving = true;
                            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.transition_left_to_new));
                            video.start();
                            new CountDownTimer(2400, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.creationnew));
                                    video.start();
                                    mode = 0;
                                }
                            }.start();
                            new CountDownTimer(3700, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    moving = false;
                                    video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.startloop));
                                    video.start();

                                }
                            }.start();

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
