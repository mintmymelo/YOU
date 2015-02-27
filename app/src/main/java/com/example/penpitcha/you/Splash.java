package com.example.penpitcha.you;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                finish();
                Intent goMain = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(goMain);
            }
        }, 4000);

    }

}
