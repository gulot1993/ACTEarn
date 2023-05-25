package com.example.actearn.feature.activity.take.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemChoicesBinding
import com.example.actearn.databinding.ItemQuizChoicesBinding
import com.example.actearn.model.entity.Choices
import com.example.actearn.model.modelview.ChoicesModelView
import com.example.actearn.model.modelview.QuizChoicesModelView

class TakeQuizChoicesAdapter(
    val context: Context,
    val items: List<QuizChoicesModelView>,
    val onAnswerChoosen: (data: QuizChoicesModelView, position: Int) -> Unit
) : RecyclerView.Adapter<TakeQuizChoicesAdapter.TakeQuizChoicesViewHolder>() {
    inner class TakeQuizChoicesViewHolder(val binding: ItemQuizChoicesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuizChoicesModelView) {
            binding.tvChoice.setText(data.choice.choiceDescription)
            binding.cbChoiceCorrectAnswer.isChecked = data.isSelected
            binding.cbChoiceCorrectAnswer.setOnClickListener {
                data.isSelected = !data.isSelected
                onAnswerChoosen.invoke(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeQuizChoicesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quiz_choices, parent, false)
        return TakeQuizChoicesViewHolder(ItemQuizChoicesBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: TakeQuizChoicesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getChoices(): List<QuizChoicesModelView> = this.items
}