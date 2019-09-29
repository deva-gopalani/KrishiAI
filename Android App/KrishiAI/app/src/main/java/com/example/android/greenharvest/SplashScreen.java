package com.example.android.greenharvest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private int SLEEP_TIMER=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //getSupportActionBar().hide();

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();

    }
    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(1000*SLEEP_TIMER);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
