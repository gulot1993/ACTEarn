package com.example.actearn.feature.activity.add.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.databinding.ItemQuestionaireBinding
import com.example.actearn.model.modelview.QuestionChoicesModelView
import timber.log.Timber

class QuestionaireAdapter(
    val context: Context,
    val questionTextChanged: (data: QuestionChoicesModelView, position: Int) -> Unit
) : RecyclerView.Adapter<QuestionaireAdapter.QuestionaireViewHolder>() {

    private var items = listOf<QuestionChoicesModelView>()
    inner class QuestionaireViewHolder(val binding: ItemQuestionaireBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuestionChoicesModelView) {
            var choicesAdapter: ChoicesAdapter? = null
            binding.etQuestion.setText(data.question)
            binding.etQuestion.addTextChangedListener {
                data.question = it.toString()
                questionTextChanged.invoke(data, adapterPosition)
            }
            binding.rvChoices.apply {
                choicesAdapter = ChoicesAdapter(
                    binding.root.context,
                    data.choices,
                    choicesTextChanged = { text, position ->
                        data.choices[position].choice = text
                        data.question = binding.etQuestion.text.toString()

                        questionTextChanged.invoke(data, adapterPosition)
                    },
                    correctIndexChecked = {
                        val items = choicesAdapter?.getChoices()
                        val currentChecked = items!![it]
                        val checkedItems = items.filter { it.isSelected && it != currentChecked }.toMutableList()
                        if (!checkedItems.isNullOrEmpty()) {
                            checkedItems.forEach {
                                val position = items.indexOf(it)
                                items[position].isSelected = false
                                choicesAdapter?.notifyItemChanged(position)
                            }
                        }
                        questionTextChanged.invoke(data, adapterPosition)
                    }
                )
                adapter = choicesAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionaireViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_questionaire, parent, false)
        return QuestionaireViewHolder(ItemQuestionaireBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: QuestionaireViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<QuestionChoicesModelView>) {
        this.items = items
    }
}