package com.findingbetteryou.faby.MoodTracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.security.Timestamp;

public class EnergyLevel extends AppCompatActivity {

    private Button proceed;
    private SeekBar seekBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView progressText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.MoodTracker", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setContentView(R.layout.activity_energy_level);
        proceed = findViewById(R.id.buttonProceedEnergyLevel);
        seekBar = findViewById(R.id.seekBar);
        progressText = findViewById(R.id.progressNumber);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("MOOD_TRACKER").child("MOOD_DATA");
        firebaseAuth = FirebaseAuth.getInstance();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putString("ENERGY_LEVEL", Integer.toString(progress));
                editor.commit();
                progressText.setText(""+progress);
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
                addDataToFirebase();
                startActivity(new Intent(EnergyLevel.this, CheckHistory.class));
                finish();
            }
        });
    }

    private void addDataToFirebase() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        String mood = sharedPreferences.getString("MOOD", "FINE");
        String energy = sharedPreferences.getString("ENERGY_LEVEL", "5");

        databaseReference.child(firebaseAuth.getUid()).child(ts).child("MOOD").setValue(mood);
        databaseReference.child(firebaseAuth.getUid()).child(ts).child("ENERGY_LEVEL").setValue(energy);
    }
}