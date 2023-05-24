package com.example.actearn.feature.activity.subject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemSubjectActivityBinding
import com.example.actearn.model.entity.Activity

class SubjectActivityAdapter(
    val context: Context,
    val items: List<Activity>,
    val onItemClicked: (data: Activity) -> Unit
): RecyclerView.Adapter<SubjectActivityAdapter.SubjectActivityViewHolder>() {
    inner class SubjectActivityViewHolder(val binding: ItemSubjectActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Activity) {
            binding.tvActivityName.text = data.activityName
            binding.tvTakeQuiz.setOnClickListener {
                onItemClicked.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectActivityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_subject_activity, parent, false)
        return SubjectActivityViewHolder(ItemSubjectActivityBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SubjectActivityViewHolder, position: Int) {
        holder.bind(items[position])
    }
}