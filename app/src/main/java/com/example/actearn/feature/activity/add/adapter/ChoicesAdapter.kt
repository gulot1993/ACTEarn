package com.example.actearn.feature.activity.add.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemChoicesBinding
import com.example.actearn.model.modelview.ChoicesModelView
import timber.log.Timber

class ChoicesAdapter(
    val context: Context,
    val items: List<ChoicesModelView>,
    val choicesTextChanged: (data: String, position: Int) -> Unit,
    val correctIndexChecked: (position: Int) -> Unit
) : RecyclerView.Adapter<ChoicesAdapter.ChoicesViewHolder>() {
    inner class ChoicesViewHolder(val binding: ItemChoicesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChoicesModelView) {
            binding.etChoice.setText(data.choice)
            binding.etChoice.addTextChangedListener {
                choicesTextChanged.invoke(it.toString(), adapterPosition)
            }

            binding.cbChoiceCorrectAnswer.isChecked = data.isSelected
            binding.cbChoiceCorrectAnswer.setOnClickListener {
                data.isSelected = !data.isSelected
                correctIndexChecked.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoicesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_choices, parent, false)
        return ChoicesViewHolder(ItemChoicesBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: ChoicesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getChoices(): List<ChoicesModelView> = items
}