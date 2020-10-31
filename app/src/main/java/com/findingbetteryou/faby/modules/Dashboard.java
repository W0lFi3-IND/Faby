package com.findingbetteryou.faby.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.findingbetteryou.faby.R;
import com.findingbetteryou.faby.caloriecal.CalorieDashboard;
import com.findingbetteryou.faby.waterlevel.WaterLevelDashboard;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.CalorieBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, CalorieDashboard.class));
            }
        });
        findViewById(R.id.WaterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, WaterLevelDashboard.class));
            }
        });

    }
}