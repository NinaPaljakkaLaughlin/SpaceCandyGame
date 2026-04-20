package com.example.spacecandygame;

/*
This file contains the Crew Member Adapter class to communicate with the recycler view in CrewStatsFragment
and display stats for each individual member of the crew

AI Usage Declaration: Gemini AI was used to write this adapter file so that it properly functions with
the recycler view. Comments have not been written by AI.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CrewMemberAdapter extends RecyclerView.Adapter<CrewMemberAdapter.ViewHolder> {

    //List of crew members created
    private List<CrewMember> crewList;

    //Constructor
    public CrewMemberAdapter(List<CrewMember> crewList) {
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew_member, parent, false);
        return new ViewHolder(view);
    }

    //Display for each crew member in recycler view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CrewMember member = crewList.get(position);
        holder.name.setText(member.getName() + " (" + member.getColor() + ")");
        holder.type.setText("Type: " + member.getCrewType());
        holder.stats.setText("XP: " + member.getXP() + " | Energy: " + member.getEnergy());
        holder.history.setText("Missions: " + member.getMissionsCompleted() + " | Training: " + member.getTrainingSessions());

        //engineer stats should show flowers planted
        if (member instanceof Engineer) {
            holder.special.setVisibility(View.VISIBLE);
            // Assuming SpecialStuff might hold total flowers, but for individual engineer:
            holder.special.setText("Flowers Planted: " + SpecialStuff.getFlowers());
            //scientist stats should show chemicals/potions created
        } else if (member instanceof Scientist) {
            holder.special.setVisibility(View.VISIBLE);
            Scientist scientist = (Scientist) member;
            holder.special.setText("Chemicals: " + scientist.getChemicals());
        } else {
            holder.special.setVisibility(View.GONE);
        }
    }

    //Method to get number of crew members
    @Override
    public int getItemCount() {
        return crewList.size();
    }

    //Method to hold each crew member in recycler view for crew stats
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, stats, history, special;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.memberName);
            type = itemView.findViewById(R.id.memberType);
            stats = itemView.findViewById(R.id.memberStats);
            history = itemView.findViewById(R.id.memberHistory);
            special = itemView.findViewById(R.id.memberSpecial);
        }
    }
}