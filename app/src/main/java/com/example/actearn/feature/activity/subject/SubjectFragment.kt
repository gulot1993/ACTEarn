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
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.StudentAnswer
import com.example.actearn.model.entity.User
import com.example.actearn.model.modelview.QuizSubjectData
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
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
    private var adapter2: SubjectActivityAdapter? = null

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
            .subscribeBy { activities ->
                Observable
                    .fromIterable(activities)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getQuestionsByActivityId(it, activities)
                    }
                    .addTo(disposables)
            }
            .addTo(disposables)
    }

    private fun getQuestionsByActivityId(activity: Activity, activities: List<Activity>) {
        val quizItemsData = mutableListOf<QuizSubjectData>()
        val answers = mutableListOf<StudentAnswer>()
        viewModel
            .getQuestionByActivityId(activityId = activity.activityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { questions ->
                Observable
                    .fromIterable(questions)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { question ->
                        viewModel
                            .getAnswersByQuestionId(question.questionId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy {
                                binding!!.tvNoActivities.isVisible = activities.isEmpty()
                                if (it.isNotEmpty()) {
                                    answers.add(it[0])
                                    if (questions.size == answers.size) {
                                        val average = (answers.filter { it.isAnswerCorrect }.count().toFloat() / questions.size.toFloat()) * 100F
                                        val isPassed = average >= 80
                                        quizItemsData.add(QuizSubjectData(activity, isPassed))

                                        adapter = SubjectActivityAdapter(
                                            requireContext(),
                                            quizItemsData
                                        ) {
                                            findNavController()
                                                .navigate(
                                                    SubjectFragmentDirections
                                                        .actionSubjectFragmentToTakeQuizFragment(it.activity.activityId)
                                                )
                                        }
                                        binding!!.rvActivities.apply {
                                            isVisible = !activities.isEmpty()
                                            adapter = this@SubjectFragment.adapter
                                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                        }
                                    }
                                } else {
                                    val emptyQuizItemsData = mutableListOf<QuizSubjectData>()
                                    val userId = viewModel.getCurrentLoggedInStudent().id
                                    answers.add(StudentAnswer(userId, question.questionId, -1, -1, false))
                                    emptyQuizItemsData.add(QuizSubjectData(activity, null))

                                    adapter2 = SubjectActivityAdapter(
                                        requireContext(),
                                        emptyQuizItemsData
                                    ) {
                                        findNavController()
                                            .navigate(
                                                SubjectFragmentDirections
                                                    .actionSubjectFragmentToTakeQuizFragment(it.activity.activityId)
                                            )
                                    }
                                    binding!!.tvAvailableActivities.isVisible = !activities.isEmpty()
                                    binding!!.rvA.apply {
                                        isVisible = !activities.isEmpty()
                                        adapter = this@SubjectFragment.adapter2
                                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                    }
                                }
                            }
                            .addTo(disposables)
                    }
                    .addTo(disposables)
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