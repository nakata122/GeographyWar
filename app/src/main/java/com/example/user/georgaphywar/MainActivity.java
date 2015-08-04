package com.example.user.georgaphywar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean[] mode={true,false};
    float scaleX=1.5f,scaleY=1.5f;
    RelativeLayout screen;
    ImageView europe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (RelativeLayout) findViewById(R.id.rel);
        europe = (ImageView) findViewById(R.id.europe);
        screen.setScaleX(scaleX);
        screen.setScaleY(scaleY);
        screen.setOnTouchListener(new View.OnTouchListener() {
            int currentX, currentY,x,y,x2,y2,currentX2=0,currentY2;
            @SuppressWarnings("deprecation")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_POINTER_2_UP:{
                        currentX = (int) event.getX(0);
                        currentY = (int) event.getY(0);
                        mode[1] = false;
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_1_UP:{
                        mode[0] = false;
                        currentX2 = (int) event.getX(1);
                        currentY2 = (int) event.getY(1);
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_1_DOWN:{
                        currentX = (int) event.getX(0);
                        currentY = (int) event.getY(0);
                        mode[0]=true;
                        mode[1] = false;
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_2_DOWN:{
                        mode[1] = true;
                        Toast.makeText(getApplication(),String.valueOf(europe.getX()),Toast.LENGTH_SHORT).show();
                        currentX2 = (int) event.getX(1);
                        currentY2 = (int) event.getY(1);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        mode[0] = true;
                        mode[1] = false;
                        currentX = (int) event.getX(0);
                        currentY = (int) event.getY(0);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if(mode[0]){
                            if(!mode[1]){
                                x = (int) event.getX(0);
                                y = (int) event.getY(0);
                                if(europe.getX()>0 && europe.getX()<=200) {
                                    europe.setX(europe.getX() - (currentX - x));
                                }else if(currentX - x>=0){
                                    europe.setX(europe.getX() - (currentX - x));
                                }
                                europe.setY(europe.getY() - (currentY - y));
                                currentX = x;
                                currentY = y; }
                            if(mode[1]){
                                if(event.getX(1)>=event.getX(0)){
                                    if(event.getX(1)>=currentX2&&scaleX<=5){
                                        scaleX+=0.1;
                                        scaleY+=0.1;
                                    }
                                    if(event.getX(1)<=currentX2&&scaleX>=1.1){
                                        scaleX-=0.1;
                                        scaleY-=0.1;
                                    }
                                }
                                else if(event.getX(1)<event.getX(0)){
                                    if(event.getX(1)<=currentX2&&scaleX<=5){
                                        scaleX+=0.1;
                                        scaleY+=0.1;
                                    }
                                    if(event.getX(1)>=currentX2&&scaleX>=1.1){
                                        scaleX-=0.1;
                                        scaleY-=0.1;
                                    }
                                }
                                screen.setScaleX(scaleX);
                                screen.setScaleY(scaleY);
                            }
                        }
                        if(!mode[0]&&mode[1]){
                            x2 = (int) event.getX(0);
                            y2 = (int) event.getY(0);
                            europe.scrollBy(currentX2 - x2,currentY2-y2);
                            currentX2 = x2;
                            currentY2 = y2;
                            break;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        break;
                    }

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
