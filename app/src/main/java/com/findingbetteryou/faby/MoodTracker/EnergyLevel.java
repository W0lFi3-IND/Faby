package com.findingbetteryou.faby.MoodTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.findingbetteryou.faby.R;

public class EnergyLevel extends AppCompatActivity {

    private Button proceed;
    private SeekBar seekBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.MoodTracker", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        setContentView(R.layout.activity_energy_level);
        proceed = findViewById(R.id.buttonProceedEnergyLevel);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putInt("ENERGY_LEVEL", progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnergyLevel.this, CheckHistory.class));
            }
        });
    }
}