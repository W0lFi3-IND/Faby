package com.findingbetteryou.faby.waterlevel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterLevelDashboard extends AppCompatActivity {
TextView tot,comp,dal;
    SharedPreferences add,show;
    float voll;
    DatabaseReference mDatabaseReference,databaseReference;
    FirebaseAuth mAuth;
    Float total;
    WaveLoadingView mWaveLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level_dashboard);
      mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setAnimDuration(3000);
        mAuth = FirebaseAuth.getInstance();
        tot = findViewById(R.id.total2);
        comp = findViewById(R.id.complete2);
        dal = findViewById(R.id.dailGoal2);
        add = getSharedPreferences("addwater",0);
        show = getSharedPreferences("WaterCap",0);
    total   = show.getFloat("totalwater",100);
        Float vol = add.getFloat("waterQty",0);
        dal.setText(""+total);
        comp.setText(""+vol);
        mWaveLoadingView.setProgressValue((int) ((vol/total)*100));
        mWaveLoadingView.setCenterTitle(String.valueOf(mWaveLoadingView.getProgressValue())+"%");

        final SharedPreferences.Editor editor = add.edit();
        FirebaseUser user;
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("WaterRecord").child(uid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("WaterData").child(uid);

        findViewById(R.id.waterImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               voll = add.getFloat("waterQty",0);
                comp.setText(""+voll);
                mWaveLoadingView.setProgressValue((int) ((voll/total)*100));
                mWaveLoadingView.setCenterTitle(String.valueOf(mWaveLoadingView.getProgressValue())+"%");
                editor.putFloat("waterQty",voll+200);
                editor.commit();
            }
        });
        findViewById(R.id.waterImageBtn).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int good=0 , bad=0;
                String id = mDatabaseReference.push().getKey();
                SharedPreferences pref =getSharedPreferences("addwater",0);
                SharedPreferences data = getSharedPreferences("waterData",0);
                SharedPreferences.Editor ed = data.edit();
                good = data.getInt("Good",0);
                bad = data.getInt("Bad",0);
                if(voll>=total)
                {
                    good = data.getInt("Good",0)+1;
                    ed.putInt("Good",good);
                    ed.apply();

                }
                else{
                    bad = data.getInt("Bad",0)+1;
                    ed.putInt("Bad",bad);
                    ed.apply();
                }
                int a = (int) pref.getFloat("waterQty",0);
                HashMap usermap = new HashMap<>();
                usermap.put("Y",a);
                mDatabaseReference.child(id).setValue(usermap);
                SharedPreferences.Editor editor = pref.edit();
                editor.putFloat("waterQty",0);
                editor.apply();
                databaseReference.child("Good").setValue(good);
                databaseReference.child("Bad").setValue(bad);
                return false;
            }
        });


    }
    }
