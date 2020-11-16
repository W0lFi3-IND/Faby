package com.findingbetteryou.faby.modules;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingbetteryou.faby.MoodTracker.MoodDashboard;
import com.findingbetteryou.faby.R;
import com.findingbetteryou.faby.caloriecal.CalorieDashboard;
import com.findingbetteryou.faby.waterlevel.WaterLevelDashboard;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
TextView h,w,a,n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        h = findViewById(R.id.Displayheight);
        w = findViewById(R.id.DisplayWeight);
        a = findViewById(R.id.DisplayAge);
        n = findViewById(R.id.displayName);

        SharedPreferences data = getSharedPreferences("data",0);
        h.setText("Height : "+data.getString("Height","")+" Cm");
        w.setText("Weight : "+data.getString("Weight","")+" Kg");
        a.setText("Age : "+data.getString("Age",""));
        n.setText(data.getString("Name",""));
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
        findViewById(R.id.moodBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, MoodDashboard.class));
            }
        });
    }
}