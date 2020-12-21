package com.findingbetteryou.faby.PeriodTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChangeSettings extends AppCompatActivity {

    private ImageView dog1;
    private ImageView dog2;
    private ImageView dog3;
    private ImageView cat1;
    private TextView cycleLength;
    private TextView periodLength;
    private SharedPreferences sharedPreferences;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);

        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.PeriodTracker", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dog1 = findViewById(R.id.dog1Image);
        dog2 = findViewById(R.id.dog2Image);
        dog3 = findViewById(R.id.dog3Image);
        cat1 = findViewById(R.id.cat1Image);
        cycleLength = findViewById(R.id.cycleLengthText);
        periodLength = findViewById(R.id.periodLengthText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("PERIOD_TRACKER").child(FirebaseAuth.getInstance().getUid());

        dog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("PET_IMAGE", "dog1");
                Toast.makeText(ChangeSettings.this, "Pet image changed successfully!", Toast.LENGTH_SHORT).show();
                editor.commit();
            }
        });

        dog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("PET_IMAGE", "dog2");
                editor.commit();
                Toast.makeText(ChangeSettings.this, "Pet image changed successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        dog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("PET_IMAGE", "dog3");
                editor.commit();
                Toast.makeText(ChangeSettings.this, "Pet image changed successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("PET_IMAGE", "cat1");
                editor.commit();
                Toast.makeText(ChangeSettings.this, "Pet image changed successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String periodLengthString = dataSnapshot.child("period_length").getValue().toString();
                    String cycleLengthString = dataSnapshot.child("cycle_length").getValue().toString();

                    cycleLength.setText("Cycle length: "+ cycleLengthString+" days");
                    periodLength.setText("Period length: "+ periodLengthString+" days");
                }
                catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}