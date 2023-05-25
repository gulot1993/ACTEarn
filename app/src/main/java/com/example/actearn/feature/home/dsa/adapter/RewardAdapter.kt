package com.example.actearn.feature.home.dsa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemRewardsBinding
import com.example.actearn.model.entity.Reward

class RewardAdapter(
    val context: Context,
    val items: List<Reward>
) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {
    inner class RewardViewHolder(val binding: ItemRewardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Reward) {
            binding.tvReward.text = data.name
            binding.tvPoints.text = data.points.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rewards, parent, false)
        return RewardViewHolder(ItemRewardsBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        holder.bind(items[position])
    }
}