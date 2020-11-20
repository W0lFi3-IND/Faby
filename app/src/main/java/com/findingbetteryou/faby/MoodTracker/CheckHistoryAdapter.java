package com.findingbetteryou.faby.MoodTracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.findingbetteryou.faby.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckHistoryAdapter extends RecyclerView.Adapter<CheckHistoryAdapter.CheckHistoryViewHolder> {
    private Context context;
    private ArrayList<MoodHistory> moodHistoryArrayList;

    public CheckHistoryAdapter(Context context, ArrayList<MoodHistory> moodHistoryArrayList) {
        this.context = context;
        this.moodHistoryArrayList = moodHistoryArrayList;
    }

    @NonNull
    @Override
    public CheckHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mood_history_layout, parent, false);
        return new CheckHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckHistoryViewHolder holder, int position) {
        holder.energyText.setText("Energy Level: "+moodHistoryArrayList.get(position).energyLevel);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(moodHistoryArrayList.get(position).timeStamp)));
        holder.timeText.setText("Recorded on: "+dateString);
        if(moodHistoryArrayList.get(position).mood.equals("RAD")){
            holder.moodImageView.setImageResource(R.drawable.rad_icon);
        }
        else if(moodHistoryArrayList.get(position).mood.equals("FINE")){
            holder.moodImageView.setImageResource(R.drawable.fine_icon);
        }
        if(moodHistoryArrayList.get(position).mood.equals("TIRED")){
            holder.moodImageView.setImageResource(R.drawable.tired_icon);
        }
        if(moodHistoryArrayList.get(position).mood.equals("SAD")){
            holder.moodImageView.setImageResource(R.drawable.sad_icon);
        }
    }

    @Override
    public int getItemCount() {
        return moodHistoryArrayList.size();
    }


    public class CheckHistoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView moodImageView;
        private TextView energyText;
        private TextView timeText;

        public CheckHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            moodImageView = itemView.findViewById(R.id.moodHistoryMoodImage);
            energyText = itemView.findViewById(R.id.moodHistoryEnergyLevel);
            timeText = itemView.findViewById(R.id.moodHistoryTime);
        }
    }
}
