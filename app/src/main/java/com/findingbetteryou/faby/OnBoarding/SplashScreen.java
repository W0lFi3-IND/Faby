package com.findingbetteryou.faby.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.findingbetteryou.faby.modules.MainActivity;
import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreen.this, PhoneVerification.class));
                    finish();
                }
            }
        },1700);
    }
    }
