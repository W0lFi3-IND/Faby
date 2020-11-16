package com.findingbetteryou.faby.MoodTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.findingbetteryou.faby.R;

public class MoodDashboard extends AppCompatActivity {
    private ImageView radImage;
    private ImageView fineImage;
    private ImageView tiredImage;
    private ImageView sadImage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_dashboard);

        radImage = findViewById(R.id.radIcon);
        fineImage = findViewById(R.id.fineIcon);
        tiredImage = findViewById(R.id.tiredIcon);
        sadImage = findViewById(R.id.sadIcon);

        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.MoodTracker", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        radImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("MOOD", "RAD");
                editor.commit();
                startActivity(new Intent(MoodDashboard.this, EnergyLevel.class));
            }
        });

        fineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("MOOD", "FINE");
                editor.commit();
                startActivity(new Intent(MoodDashboard.this, EnergyLevel.class));
            }
        });

        tiredImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("MOOD", "TIRED");
                editor.commit();
                startActivity(new Intent(MoodDashboard.this, EnergyLevel.class));
            }
        });

        sadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("MOOD", "SAD");
                editor.commit();
                startActivity(new Intent(MoodDashboard.this, EnergyLevel.class));
            }
        });
    }
}