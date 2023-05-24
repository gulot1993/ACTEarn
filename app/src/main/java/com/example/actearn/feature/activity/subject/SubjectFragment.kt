package com.example.actearn.feature.activity.subject

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentSubjectBinding
import com.example.actearn.feature.activity.subject.adapter.SubjectActivityAdapter
import com.example.actearn.model.entity.User
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class SubjectFragment :
    BaseFragment<FragmentSubjectBinding>(),
    AdapterView.OnItemSelectedListener {

    private val viewModel: SubjectViewModel by activityViewModels()
    private val disposables = CompositeDisposable()
    private var adapter: SubjectActivityAdapter? = null

    override fun resId(): Int {
        return R.layout.fragment_subject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        getProfessors()
        setupListeners()
    }

    private fun setupListeners() {
        binding!!
            .tvGetActivities
            .setOnClickListener {
                val subjects = resources.getStringArray(R.array.subjects)
                val professor = viewModel.getProfessorByIndex(binding!!.spinner2.selectedItemPosition)
                val subject = subjects[binding!!.spinner.selectedItemPosition]
                getActivity(professor.userId, subject)
            }
    }

    private fun getActivity(profId: Int, subject: String) {
        viewModel
            .getActivityByProfAndSubject(profId, subject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Timber.d("activity taken: $it")
                adapter = SubjectActivityAdapter(
                    requireContext(),
                    it
                ) {
                    findNavController()
                        .navigate(
                            SubjectFragmentDirections
                                .actionSubjectFragmentToTakeQuizFragment(it.activityId)
                        )
                }
                binding!!.tvNoActivities.isVisible = it.isEmpty()
                binding!!.rvActivities.apply {
                    isVisible = !it.isEmpty()
                    adapter = this@SubjectFragment.adapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
            .addTo(disposables)
    }

    private fun getProfessors() {
        viewModel
            .getAllProfessors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModel.professors(it)
                setupProfessorSpinner(it)
            }
            .addTo(disposables)
    }

    private fun setupProfessorSpinner(users: List<User>) {
        val adapter = object: ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item, users.map { "${it.firstname} ${it.lastname}" }) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent)
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinner2.isVisible = true
        binding!!.tvSelectProfessor.isVisible = true
        binding!!.spinner2.adapter = adapter
        binding!!.spinner2.onItemSelectedListener = this
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
        disposables.clear()
        super.onDestroyView()
    }
}