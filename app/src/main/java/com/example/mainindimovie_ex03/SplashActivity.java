package com.example.mainindimovie_ex03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//스플래쉬 Activitiy
public class SplashActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //시간 지연을 위한 코드
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}