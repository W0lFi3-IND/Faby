package com.findingbetteryou.faby.MoodTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetUpGoals extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button addGoals;
    private LinearLayout goalsLinearLayout;
    private RecyclerView goalsRecyclerView;
    private ArrayList<GoalDetails> goalDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_goals);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("MOOD_TRACKER").child("GOAL_DATA").
                child(FirebaseAuth.getInstance().getUid());

        addGoals = findViewById(R.id.addGoalsButton);
        goalsLinearLayout = findViewById(R.id.goalsLinearLayout);
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);

        goalDetailsArrayList = new ArrayList<>();

        if(databaseReference != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    goalDetailsArrayList.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String goal = snapshot.getKey();
                        String checkpointReached = snapshot.getValue().toString();

                        GoalDetails goalDetails = new GoalDetails(goal, checkpointReached);
                        goalDetailsArrayList.add(goalDetails);
                    }

                    if(goalDetailsArrayList.size() == 0){
                        goalsLinearLayout.removeAllViews();
                        View view = getLayoutInflater().inflate(R.layout.no_data_layout, goalsLinearLayout, false);
                        goalsLinearLayout.addView(view);
                        return;
                    }

                    GoalsAdapter goalsAdapter = new GoalsAdapter(SetUpGoals.this, goalDetailsArrayList);
                    goalsRecyclerView.setAdapter(goalsAdapter);
                    goalsRecyclerView.setLayoutManager(new LinearLayoutManager(SetUpGoals.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            goalsLinearLayout.removeAllViews();
            View view = getLayoutInflater().inflate(R.layout.no_data_layout, goalsLinearLayout, false);
            goalsLinearLayout.addView(view);
        }

        addGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(SetUpGoals.this);
                AlertDialog dialog = new AlertDialog.Builder(SetUpGoals.this)
                        .setTitle("Add a new goal")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                databaseReference.child(task).setValue("0");
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }
}