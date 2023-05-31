package com.example.actearn.feature.activity.subject.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentAddSubjectBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class AddSubjectFragment : BaseFragment<FragmentAddSubjectBinding>() {
    private val viewModel: AddSubjectViewModel by activityViewModels()
    private val disposables = CompositeDisposable()
    override fun resId(): Int {
        return R.layout.fragment_add_subject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvAddSubject?.setOnClickListener {
            viewModel.addSubject(binding!!.etSubjectName.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "subject added", Toast.LENGTH_LONG).show()
                }
                .addTo(disposables)
        }
    }
}