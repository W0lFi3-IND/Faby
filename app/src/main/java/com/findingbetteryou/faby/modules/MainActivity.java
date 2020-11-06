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

                Float aa = Float.valueOf(sharedPreferences.getString("Age","0"));
                int ww = Integer.valueOf(sharedPreferences.getString("Weight","0"));
                float t1 = (float)(ww/30);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("WaterCap", 0); // 0 - for private mode
                final SharedPreferences.Editor editorr = pref1.edit();
                editorr.putFloat("totalwater",t1*1000);
                editorr.commit();

                //adddwater
                final SharedPreferences addwater = getApplicationContext().getSharedPreferences("AddWater",0);
                final SharedPreferences.Editor editor1 = addwater.edit();
                editor1.putFloat("waterQty",0);
                editor1.commit();

                SharedPreferences waterData = getSharedPreferences("waterData",0);
                SharedPreferences.Editor eeditor = waterData.edit();
                eeditor.putInt("Good",0);
                eeditor.putInt("Bad",0);
                eeditor.commit();
                startActivity(new Intent(MainActivity.this,Dashboard.class));
                finish();
            }
        });
    }
}