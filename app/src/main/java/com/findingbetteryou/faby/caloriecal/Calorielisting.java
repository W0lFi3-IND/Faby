package com.findingbetteryou.faby.caloriecal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.findingbetteryou.faby.R;

import java.util.List;

public class Calorielisting extends ArrayAdapter<CalDetails> {
    private Activity context;
    private List<CalDetails> list;

    public Calorielisting(Activity context, List<CalDetails> list){
        super(context,R.layout.caloriehistory_layout,list);
        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.caloriehistory_layout,null);
        TextView t1=listviewitem.findViewById(R.id.text);
        TextView t2=listviewitem.findViewById(R.id.text1);
        CalDetails db=list.get(position);
        t1.setText("-"+db.getItem().toUpperCase());
        t2.setText(db.getTotalcal());
        return listviewitem;
    }
}
