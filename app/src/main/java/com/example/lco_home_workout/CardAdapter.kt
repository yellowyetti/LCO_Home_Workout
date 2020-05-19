package com.example.lco_home_workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val cardSet: Array<WorkoutInfo>): RecyclerView.Adapter<CardAdapter.CardViewHolder>(){

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemWorkoutName: TextView = itemView.findViewById(R.id.text_exercise_name)
        var itemWorkoutDuration: TextView = itemView.findViewById(R.id.text_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.workout_card, parent, false)
        return CardViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cardSet.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val workout = cardSet[position]
        holder.itemWorkoutName.text = workout.name
        holder.itemWorkoutDuration.text = workout.duration.toString()
    }
}