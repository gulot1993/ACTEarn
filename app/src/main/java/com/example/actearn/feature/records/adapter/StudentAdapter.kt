package com.example.actearn.feature.records.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemRewardsBinding
import com.example.actearn.model.entity.User

class StudentAdapter(
    val context: Context,
    val users: List<User>,
    val onUserClicked: (user: User) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){
    inner class StudentViewHolder(val binding: ItemRewardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvReward.text = "${user.firstname} ${user.lastname}"
            binding.tvPoints.text = "View Records"

            binding.tvPoints.setOnClickListener {
                onUserClicked.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rewards, parent, false)
        return StudentViewHolder(ItemRewardsBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return this.users.count()
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(users[position])
    }
}