package com.example.actearn.feature.activity.add

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentAddActivityBinding
import com.example.actearn.feature.activity.add.adapter.QuestionaireAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddActivityFragment :
    BaseFragment<FragmentAddActivityBinding>(),
    AdapterView.OnItemSelectedListener{

    private val viewModel: AddActivityViewModel by activityViewModels()
    private var adapter: QuestionaireAdapter? = null
    override fun resId(): Int {
        return R.layout.fragment_add_activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupListeners()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        adapter = QuestionaireAdapter(
            requireContext(),
            questionTextChanged = { data, position ->
                viewModel.updateQuestionaire(data, position)
            }
        )
        binding?.rvQuestionaires?.apply {
            adapter = this@AddActivityFragment.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupViewModel() {
        viewModel.questionsWithChoices.observe(viewLifecycleOwner) {
            it?.let {
                setupRecyclerView()
                adapter?.setItems(it)
            }
        }
    }

    private fun setupListeners() {
        binding?.btnSubmit?.setOnClickListener {

        }

        binding?.tvAddQuestionaires?.setOnClickListener {
            viewModel.addEmptyQuestionaire()
        }
    }

    private fun setupSpinner() {
        val subjects = resources.getStringArray(R.array.subjects)
        val adapter = object: ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item, subjects) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent)
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinner.adapter = adapter
        binding!!.spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}