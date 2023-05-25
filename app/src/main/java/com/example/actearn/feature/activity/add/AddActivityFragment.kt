package com.example.actearn.feature.activity.add

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentAddActivityBinding
import com.example.actearn.feature.activity.add.adapter.QuestionaireAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class AddActivityFragment :
    BaseFragment<FragmentAddActivityBinding>(),
    AdapterView.OnItemSelectedListener{

    private val viewModel: AddActivityViewModel by activityViewModels()
    private var adapter: QuestionaireAdapter? = null
    private val disposables = CompositeDisposable()
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

        viewModel.doneSaving.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Activity Saved!", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupListeners() {
        binding?.btnSubmit?.setOnClickListener {
            binding?.btnSubmit?.isEnabled = false
            val subjects = resources.getStringArray(R.array.subjects)
            val subject = subjects[binding?.spinner?.selectedItemPosition ?: 0]
            val activity = binding?.etActivityName?.text.toString()
            viewModel.saveQuestions(activity, subject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    getActivityByName(activity)
                }
                .addTo(compositeDisposable = disposables)

        }

        binding?.tvAddQuestionaires?.setOnClickListener {
            viewModel.addEmptyQuestionaire()
        }
    }
    private fun getActivityByName(name: String) {
        viewModel.getActivityByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModel.insertQuestions(it.activityId)
            }
            .addTo(compositeDisposable = disposables)
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
        disposables.clear()
        super.onDestroyView()
    }
}