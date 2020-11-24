package com.findingbetteryou.faby.caloriecal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.findingbetteryou.faby.R;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class CalorieDashboard extends AppCompatActivity {
TextView textdate;
TextView textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_dashboard);
        textdate = findViewById(R.id.dateview);
        findViewById(R.id.textView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalorieDashboard.this,Calhistory.class));
            }
        });
        setDateText();
       /* findViewById(R.id.addSnacksBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalorieDashboard.this,DetectCalorie.class));
            }
        });
        findViewById(R.id.addDinnerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalorieDashboard.this,DetectCalorie.class));
            }
        });
       findViewById(R.id.addLunchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalorieDashboard.this,DetectCalorie.class));
            }
        });*/
        findViewById(R.id.addBreakfastBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalorieDashboard.this,DetectCalorie.class));
            }
        });
    }
    void setDateText()
    {
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int dddate = cal.get(Calendar.DATE);

        switch (month)
        {
            case 0: textdate.setText("On "+dddate+" "+"Jan");break;
            case 1: textdate.setText("On "+dddate+" "+"Feb");break;
            case 2: textdate.setText("On "+dddate+" "+"Mar");break;
            case 3: textdate.setText("On "+dddate+" "+"Apr");break;
            case 4: textdate.setText("On "+dddate+" "+"May");break;
            case 5: textdate.setText("On "+dddate+" "+"Jun");break;
            case 6: textdate.setText("On "+dddate+" "+"Jul");break;
            case 7: textdate.setText("On "+dddate+" "+"Aug");break;
            case 8: textdate.setText("On "+dddate+" "+"Sept");break;
            case 9: textdate.setText("On "+dddate+" "+"Oct");break;
            case 10: textdate.setText("On "+dddate+" "+"Nov");break;
            case 11: textdate.setText("On "+dddate+" "+"Dec");break;
            default:
                Log.d("DateError", "setDateText: ");
        }

    }
}