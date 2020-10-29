package com.findingbetteryou.faby.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.findingbetteryou.faby.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.CreateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Dashboard.class));
            }
        });
    }
}