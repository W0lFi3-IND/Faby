package com.findingbetteryou.faby.PeriodTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findingbetteryou.faby.MoodTracker.SetUpGoals;
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

public class PeriodTrackerDashboard extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button periodStarts;
    private TextView dayText;
    private TextView changeSettings;
    private ImageView petImage;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tracker_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("PERIOD_TRACKER").child(firebaseAuth.getUid());

        periodStarts = findViewById(R.id.periodStartsButton);
        dayText = findViewById(R.id.dayText);
        changeSettings = findViewById(R.id.changeSettingsText);
        petImage = findViewById(R.id.petImage);

        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.PeriodTracker", Context.MODE_PRIVATE);

        String pet = sharedPreferences.getString("PET_IMAGE", "dog1");

        if(pet.equals("dog1")){
            petImage.setImageResource(R.drawable.dog1);
        }
        else if (pet.equals("dog2")){
            petImage.setImageResource(R.drawable.dog2);
        }
        else if (pet.equals("dog3")){
            petImage.setImageResource(R.drawable.dog3);
        }
        else {
            petImage.setImageResource(R.drawable.cat1);
        }

        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeriodTrackerDashboard.this, ChangeSettings.class));
            }
        });

        periodStarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                databaseReference.child("period_starts").setValue(date);
            }
        });



        if(databaseReference != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String startDate = dataSnapshot.child("period_starts").getValue().toString();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String periodLength = dataSnapshot.child("period_length").getValue().toString();
                        try {
                            Date start;
                            Date current;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                            start = simpleDateFormat.parse(startDate);
                            current = simpleDateFormat.parse(currentDate);

                            //Comparing dates
                            long difference = Math.abs(start.getTime() - current.getTime());
                            long differenceDates = (difference / (24 * 60 * 60 * 1000)) + 1;

                            String dayDifference = Long.toString(differenceDates);
                            if (Integer.parseInt(dayDifference) > Integer.parseInt(periodLength)) {
                                dayText.setText("");
                            } else {
                                dayText.setText(dayDifference+" day");
                            }

                        } catch (Exception e) {

                        }
                    }
                    catch (Exception e){
                        final LinearLayout linearLayout = new LinearLayout(PeriodTrackerDashboard.this);
                        final EditText periodLengthEditText = new EditText(PeriodTrackerDashboard.this);
                        final EditText cycleLengthEditText = new EditText(PeriodTrackerDashboard.this);

                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        periodLengthEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        cycleLengthEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                        linearLayout.addView(periodLengthEditText);
                        linearLayout.addView(cycleLengthEditText);
                        AlertDialog dialog = new AlertDialog.Builder(PeriodTrackerDashboard.this)
                                .setTitle("Enter details")
                                .setMessage("Period length")
                                .setView(linearLayout)
                                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String periodLength = String.valueOf(periodLengthEditText.getText());
                                        String cycleLength = String.valueOf(cycleLengthEditText.getText());

                                        databaseReference.child("period_length").setValue(periodLength);
                                        databaseReference.child("cycle_length").setValue(cycleLength);
                                    }
                                })
                                .create();
                        dialog.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("com.findingbetteryou.faby.PeriodTracker", Context.MODE_PRIVATE);

        String pet = sharedPreferences.getString("PET_IMAGE", "dog1");

        if(pet.equals("dog1")){
            petImage.setImageResource(R.drawable.dog1);
        }
        else if (pet.equals("dog2")){
            petImage.setImageResource(R.drawable.dog2);
        }
        else if (pet.equals("dog3")){
            petImage.setImageResource(R.drawable.dog3);
        }
        else {
            petImage.setImageResource(R.drawable.cat1);
        }
    }
}