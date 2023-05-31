package com.example.actearn.feature.activity.subject.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemSubjectActivityBinding
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.ActivityWithRemarks
import com.example.actearn.model.modelview.QuizSubjectData

class SubjectActivityAdapter(
    val context: Context,
    val items: List<ActivityWithRemarks>,
    val onItemClicked: (data: ActivityWithRemarks) -> Unit
): RecyclerView.Adapter<SubjectActivityAdapter.SubjectActivityViewHolder>() {
    inner class SubjectActivityViewHolder(val binding: ItemSubjectActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityWithRemarks) {
            val context = binding.root.context
            binding.tvActivityName.text = data.activity.activityName
            binding.tvRemarks.apply {
                setTextColor(ColorStateList.valueOf(context.getColor(
                    if (data.remarks != null) {
                        if (data.remarks.remarks.equals("Passed", ignoreCase = true)) R.color.green else R.color.red
                    } else {
                        R.color.black
                    }
                )))

                text = if (data.remarks != null) {
                    data.remarks.remarks
                } else {
                    "-"
                }
            }
            binding.tvTakeQuiz.apply {
                isEnabled = data.remarks == null
                isClickable = data.remarks == null
                setOnClickListener {
                    onItemClicked.invoke(data)
                }

                text = if (data.remarks != null) {
                    "Done"
                } else {
                    "Take Quiz"
                }

                setTextColor(
                    ColorStateList.valueOf(
                        context.getColor(
                            if (data.remarks != null) {
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