package com.example.actearn.feature.activity.take.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemQuestionaireBinding
import com.example.actearn.databinding.ItemQuizQuestionaireBinding
import com.example.actearn.feature.activity.add.adapter.ChoicesAdapter
import com.example.actearn.model.modelview.QuestionChoicesModelView
import com.example.actearn.model.modelview.QuizQuestionChoicesModelView
import timber.log.Timber

class TakeQuizQuestionAdapter(
    val context: Context,
    val questionItemChanged: (data: QuizQuestionChoicesModelView, position: Int) -> Unit
) : RecyclerView.Adapter<TakeQuizQuestionAdapter.TakeQuizQuestionViewHolder>(){
    private var items = listOf<QuizQuestionChoicesModelView>()
    inner class TakeQuizQuestionViewHolder(val binding: ItemQuizQuestionaireBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuizQuestionChoicesModelView) {
            var choicesAdapter: TakeQuizChoicesAdapter? = null
            binding.tvQuestion.text = "${adapterPosition + 1}.) ${data.question.question}"
            Timber.d("data are: ${data.question}")
            binding.rvChoices.apply {
                choicesAdapter = TakeQuizChoicesAdapter(
                    binding.root.context,
                    data.choices
                ) { choice, position ->
                    val items = choicesAdapter?.getChoices()
                    val currentChecked = items!![position]
                    val checkedItems = items.filter { it.isSelected && it != currentChecked }.toMutableList()
                    Timber.d("Checked items: $checkedItems $currentChecked")
                    if (!checkedItems.isNullOrEmpty()) {
                        checkedItems.forEach {
                            val position = items.indexOf(it)
                            items[position].isSelected = false
                            choicesAdapter?.notifyItemChanged(position)
                        }
                    }
                    data.choices = items.toMutableList()
                    questionItemChanged.invoke(data, adapterPosition)
                }
                adapter = choicesAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeQuizQuestionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quiz_questionaire, parent, false)
        return TakeQuizQuestionViewHolder(ItemQuizQuestionaireBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: TakeQuizQuestionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<QuizQuestionChoicesModelView>) {
        this.items = items
    }
}