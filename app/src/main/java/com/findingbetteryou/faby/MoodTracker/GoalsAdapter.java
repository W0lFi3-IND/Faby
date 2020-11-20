package com.findingbetteryou.faby.MoodTracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.findingbetteryou.faby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsAdapterViewHolder> {
    private Context context;
    private ArrayList<GoalDetails> goalDetailsArrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public GoalsAdapter(Context context, ArrayList<GoalDetails> goalDetailsArrayList) {
        this.context = context;
        this.goalDetailsArrayList = goalDetailsArrayList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("MOOD_TRACKER").child("GOAL_DATA").child(FirebaseAuth.getInstance().getUid());

    }

    @NonNull
    @Override
    public GoalsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.goal_details_layout, parent, false);
        return new GoalsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GoalsAdapterViewHolder holder, final int position) {
        holder.goalSeekBar.setEnabled(false);
        holder.goalTextView.setText(goalDetailsArrayList.get(position).getGoal());
        holder.goalSeekBar.setProgress(Integer.parseInt(goalDetailsArrayList.get(position).getCheckpointReached()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(goalDetailsArrayList.get(position).getGoal()).setValue(
                        Integer.toString(Integer.parseInt(goalDetailsArrayList.get(position).getCheckpointReached()) + 1));

                holder.goalSeekBar.setProgress(Integer.parseInt(goalDetailsArrayList.get(position).getCheckpointReached()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalDetailsArrayList.size();
    }

    public class GoalsAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView goalTextView;
        private TextView plus;
        private SeekBar goalSeekBar;
        public GoalsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            goalTextView = itemView.findViewById(R.id.goal);
            plus = itemView.findViewById(R.id.plus);
            goalSeekBar = itemView.findViewById(R.id.goalsSeekBar);
        }
    }
}
