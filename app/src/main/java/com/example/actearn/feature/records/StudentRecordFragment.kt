package com.example.actearn.feature.records

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentStudentRecordsBinding
import com.example.actearn.feature.activity.subject.SubjectFragmentDirections
import com.example.actearn.feature.activity.subject.adapter.SubjectActivityAdapter
import com.example.actearn.feature.records.adapter.StudentAdapter
import com.example.actearn.model.entity.*
import com.example.actearn.model.modelview.QuizSubjectData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class StudentRecordFragment :
    BaseFragment<FragmentStudentRecordsBinding>(),
    AdapterView.OnItemSelectedListener {

    private val disposables = CompositeDisposable()
    private val viewModel: StudentRecordViewModel by activityViewModels()

    private var adapter: StudentAdapter? = null

    override fun resId(): Int {
        return R.layout.fragment_student_records
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupListener()
    }

    private fun setupListener() {
        binding!!.tvGetRecords.setOnClickListener {
            val subjects = resources.getStringArray(R.array.subjects)
            val subject = subjects[binding!!.spinner.selectedItemPosition]
            val activity = viewModel.getSelectedActivity(binding!!.spinner2.selectedItemPosition)

            getQuestionsByActivityId(activity.activityId)
        }
    }

    private fun getQuestionsByActivityId(activityId: Int) {
        val users = mutableListOf<User>()
        val quizItemsData = mutableListOf<StudentAnswer>()
        viewModel
            .getDistinctUserId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        viewModel
                            .getUserById(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy {
                                val points = it[0].points.sumOf { it.points }.toString()
                                it.map {
                                   users.add(it.user)
                                }
                                adapter = StudentAdapter(
                                    requireContext(),
                                    users
                                ) { user ->
                                    viewModel
                                        .getQuestionByActivityId(activityId = activityId)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeBy { questions ->
                                            val questionsList = mutableListOf<Question>()
                                            questionsList.addAll(questions)
                                            Observable
                                                .fromIterable(questions)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeBy {
                                                    viewModel
                                                        .getAnswersByQuestionId(it.questionId, user.userId)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribeBy {
                                                            if (it.isEmpty()) {
                                                                Toast.makeText(requireContext(), "Not taken exam yet", Toast.LENGTH_LONG).show()
                                                            } else {
                                                                quizItemsData.add(it[0])
                                                                if (quizItemsData.size == questionsList.size) {
                                                                    var average = 0F
                                                                    val passed = if (quizItemsData.isNotEmpty())  {
                                                                        average = (quizItemsData.filter { it.isAnswerCorrect }.count().toFloat() / questionsList.size.toFloat()) * 100F
                                                                        average >= 80
                                                                    } else {
                                                                        false
                                                                    }

                                                                    Timber.d("correct answer count ${quizItemsData.map { it.isAnswerCorrect }.count()} ${questionsList.size}")

                                                                    Timber.d("total points $")
                                                                    showBottomSheet(average, user, passed, points)
                                                                }
                                                            }

                                                        }
                                                        .addTo(disposables)
                                                }
                                                .addTo(disposables)
                                        }
                                        .addTo(disposables)
                                }
                                binding!!.rvRecords.adapter = adapter
                                binding!!.rvRecords.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            }
                            .addTo(disposables)
                    }
                    .addTo(disposables)
            }
            .addTo(disposables)

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
        val subjects = resources.getStringArray(R.array.subjects)
        val subject = subjects[position]

        viewModel
            .getActivitiesBySubject(subject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModel.addActivities(it)
                setupSpinner2(activities = it)
            }
            .addTo(disposables)
    }

    private fun setupSpinner2(activities: List<Activity>) {
        val adapter = object: ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item, activities.map { it.activityName }) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent)
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinner2.adapter = adapter
        binding!!.spinner2.isVisible = true

        binding!!.tvSelectActivity.isVisible = true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onDestroy() {
        adapter = null
        disposables.clear()
        super.onDestroy()
    }

    private fun showBottomSheet(average: Float, user: User, isPassed: Boolean, usrPoints: String) {
        val bottomsheet = BottomSheetDialog(requireContext())
        bottomsheet.setContentView(R.layout.layout_bottomsheet_user_data)
        val name = bottomsheet.findViewById<MaterialTextView>(R.id.tvStudentName)
        val remarks = bottomsheet.findViewById<MaterialTextView>(R.id.tvRemarks)
        val points = bottomsheet.findViewById<MaterialTextView>(R.id.tvPoints)
        remarks?.text = if (isPassed) "Passed" else "Failed"
        name?.text = "${user.firstname} ${user.lastname}"
        points?.text = "Total points: $usrPoints"
        bottomsheet.show()
    }
}