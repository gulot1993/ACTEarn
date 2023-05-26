package com.example.actearn.feature.home.dsa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemRewardsBinding
import com.example.actearn.model.entity.Reward
import com.example.actearn.model.modelview.StudentClaimedReward

class StudentClaimedRewardAdapter(
    val context: Context,
    val items: List<StudentClaimedReward>
) : RecyclerView.Adapter<StudentClaimedRewardAdapter.StudentClaimRewardViewHolder>() {
    inner class StudentClaimRewardViewHolder(val binding: ItemRewardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StudentClaimedReward) {
            binding.tvReward.text = data.studentName
            binding.tvPoints.text = data.rewardNane
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentClaimRewardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rewards, parent, false)
        return StudentClaimRewardViewHolder(ItemRewardsBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: StudentClaimRewardViewHolder, position: Int) {
        holder.bind(items[position])
    }
}