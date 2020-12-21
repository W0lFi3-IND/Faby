package com.findingbetteryou.faby.MoodTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.findingbetteryou.faby.PeriodTracker.PeriodTrackerDashboard;
import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckHistory extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ArrayList<MoodHistory> moodHistoryArrayList;
    private Button setUpGoalsButton;
    private LinearLayout moodHistoryLinearLayout;
    private RecyclerView moodHistoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);

        setUpGoalsButton = findViewById(R.id.setUpGoalsButton);
        moodHistoryLinearLayout = findViewById(R.id.moodHistoryLinearLayout);
        moodHistoryRecyclerView = findViewById(R.id.moodHistoryRecyclerView);

        moodHistoryArrayList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("MOOD_TRACKER").child("MOOD_DATA").child(firebaseAuth.getUid());

        setUpGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckHistory.this, SetUpGoals.class));
            }
        });

        if(databaseReference != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    moodHistoryArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String timeStamp = snapshot.getKey();
                        String mood = snapshot.child("MOOD").getValue().toString();
                        String energyLevel = snapshot.child("ENERGY_LEVEL").getValue().toString();

                        MoodHistory moodHistory = new MoodHistory(timeStamp, energyLevel, mood);
                        moodHistoryArrayList.add(moodHistory);
                    }

                    if (moodHistoryArrayList.size() == 0) {
                        moodHistoryLinearLayout.removeAllViews();
                        View view = getLayoutInflater().inflate(R.layout.no_data_layout, moodHistoryLinearLayout, false);
                        moodHistoryLinearLayout.addView(view);
                        return;
                    }

                    CheckHistoryAdapter driverJobDoneAdapter = new CheckHistoryAdapter(CheckHistory.this, moodHistoryArrayList);
                    moodHistoryRecyclerView.setAdapter(driverJobDoneAdapter);
                    moodHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(CheckHistory.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //do nothing
                }
            });
        }
        else{
            moodHistoryLinearLayout.removeAllViews();
            View view = getLayoutInflater().inflate(R.layout.no_data_layout, moodHistoryLinearLayout, false);
            moodHistoryLinearLayout.addView(view);
        }

    }
}