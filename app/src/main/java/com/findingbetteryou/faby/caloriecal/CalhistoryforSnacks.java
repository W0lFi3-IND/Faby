
package com.findingbetteryou.faby.caloriecal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class CalhistoryforSnacks extends AppCompatActivity {
    ListView listview;
    DatabaseReference databaseReference;
    List<CalDetails> list;
    public FirebaseAuth firebaseAuth;
    TextView textView3;
    TextView textdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calhistoryfor_snacks);
        firebaseAuth = FirebaseAuth.getInstance();
        listview=findViewById(R.id.listview);
        textView3=findViewById(R.id.textView3);
        textdate = findViewById(R.id.dateview);
        setDateText();
    }
    @Override
    protected void onStart() {
        super.onStart();
        String uid = firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Calorie_Recorder").child("Snacks").child(uid);
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list=new ArrayList<CalDetails>();
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        CalDetails db = a.getValue(CalDetails.class);
                        list.add(db);
                    }
                    Calorielisting adapter = new Calorielisting(CalhistoryforSnacks.this, list);
                    listview.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
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

