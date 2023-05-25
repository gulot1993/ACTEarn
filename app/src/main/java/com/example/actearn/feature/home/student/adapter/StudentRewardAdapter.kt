package com.example.actearn.feature.home.student.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.actearn.R
import com.example.actearn.databinding.ItemClaimRewardBinding
import com.example.actearn.model.entity.Reward

class StudentRewardAdapter(
    val context: Context,
    val items: List<Reward>,
    val onRewardClaimed: (data: Reward) -> Unit
) : RecyclerView.Adapter<StudentRewardAdapter.StudentRewardViewHolder>(){
    inner class StudentRewardViewHolder(val binding: ItemClaimRewardBinding) : ViewHolder(binding.root) {
        fun bind(reward: Reward) {
            binding.tvPoints.text = "Points needed ${reward.points}"
            binding.tvItemName.text = reward.name

            binding.tvClaim.setOnClickListener {
                onRewardClaimed.invoke(reward)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentRewardViewHolder {
        return StudentRewardViewHolder(
            ItemClaimRewardBinding.bind(
                LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_claim_reward, parent, false)
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: StudentRewardViewHolder, position: Int) {
        holder.bind(items[position])
    }
}