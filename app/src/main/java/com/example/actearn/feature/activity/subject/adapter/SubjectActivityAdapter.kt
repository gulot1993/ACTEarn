package com.example.actearn.feature.activity.subject.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemSubjectActivityBinding
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.modelview.QuizSubjectData

class SubjectActivityAdapter(
    val context: Context,
    val items: List<QuizSubjectData>,
    val onItemClicked: (data: QuizSubjectData) -> Unit
): RecyclerView.Adapter<SubjectActivityAdapter.SubjectActivityViewHolder>() {
    inner class SubjectActivityViewHolder(val binding: ItemSubjectActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuizSubjectData) {
            val context = binding.root.context
            binding.tvActivityName.text = data.activity.activityName

            binding.tvRemarks.apply {
                setTextColor(ColorStateList.valueOf(context.getColor(
                    if (data.isPassed != null) {
                        if (data.isPassed) R.color.green else R.color.red
                    } else {
                        R.color.black
                    }
                )))

                text = if (data.isPassed != null) {
                    if (data.isPassed == true) "Passed" else "Failed"
                } else {
                    "-"
                }
            }
            binding.tvTakeQuiz.apply {
                setOnClickListener {
                    onItemClicked.invoke(data)
                }

                text = if (data.isPassed != null) {
                    "Done"
                } else {
                    "Take Quiz"
                }

                setTextColor(
                    ColorStateList.valueOf(
                        context.getColor(
                            if (data.isPassed != null) {
                                R.color.black
                            } else {
                                R.color.teal_700
                            }
                        )
                    )
                )
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