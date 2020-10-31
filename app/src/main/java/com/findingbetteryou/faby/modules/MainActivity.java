package com.findingbetteryou.faby.modules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.findingbetteryou.faby.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
TextInputEditText name,age,height,weight;
RadioGroup rg;
RadioButton maleFemale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.username);
        weight = findViewById(R.id.userweight);
        age = findViewById(R.id.userage);
        height = findViewById(R.id.userheight);
        rg = findViewById(R.id.rg);

        findViewById(R.id.CreateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int id = rg.getCheckedRadioButtonId();
                maleFemale = findViewById(id);
                editor.putString("Name",name.getText().toString());
                editor.putString("Weight",weight.getText().toString());
                editor.putString("Age",age.getText().toString());
                editor.putString("Height",height.getText().toString());
                editor.putString("Gender",maleFemale.getText().toString());
                editor.commit();

                startActivity(new Intent(MainActivity.this,Dashboard.class));
                finish();
            }
        });
    }
}