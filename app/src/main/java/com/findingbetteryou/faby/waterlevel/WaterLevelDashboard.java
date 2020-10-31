package com.findingbetteryou.faby.waterlevel;

import android.os.Bundle;

import com.findingbetteryou.faby.R;

import androidx.appcompat.app.AppCompatActivity;
import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterLevelDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level_dashboard);
        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.setCenterTitle(String.valueOf(mWaveLoadingView.getProgressValue())+"%");
    }
}