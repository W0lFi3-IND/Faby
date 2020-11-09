package com.findingbetteryou.faby.waterlevel;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingbetteryou.faby.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterLevelDashboard extends AppCompatActivity {
TextView tot,comp,dal;
    SharedPreferences add,show;
    float voll;
    LineChart mLineChart;
    DatabaseReference mDatabaseReference,databaseReference;
    FirebaseAuth mAuth;
    Float total;
    WaveLoadingView mWaveLoadingView;
    ArrayList<Entry> X;
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
        mLineChart = findViewById(R.id.chart);
        mLineChart.setTouchEnabled(true);
        mLineChart.setPinchZoom(true);
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
        avg();
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

retrivedata();
    }
    private void avg() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                X = new ArrayList<>();
                float total = 0;
                List<Float> list = new ArrayList<>();
                long count = dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {  String va = ds.child("Y").getValue().toString();
                    float XA = Float.parseFloat(va);
                    total+= XA;

                    list.add(XA);
                }
                Collections.sort(list);
                if(!list.isEmpty()) {
                    float ans = total/1000;
                    tot.setText("" + ans+" Lt");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void retrivedata() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                X = new ArrayList<>();
                float i = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    i=i+1;
                    String va = ds.child("Y").getValue().toString();
                    float XA = Float.parseFloat(va);
                    X.add(new Entry(i,XA));
                    final LineDataSet set1 = new LineDataSet(X,"WaterValue");
                    LineData data = new LineData(set1);
                    set1.setDrawIcons(false);
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.DKGRAY);
                    set1.setCircleColor(Color.DKGRAY);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);
                    mLineChart.setData(data);
                    mLineChart.notifyDataSetChanged();
                    mLineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
