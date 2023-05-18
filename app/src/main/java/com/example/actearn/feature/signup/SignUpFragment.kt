package com.example.actearn.feature.signup

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignupBinding>(),
    AdapterView.OnItemSelectedListener {

    private val viewModel: SignUpViewModel by activityViewModels()
    private val roles = arrayOf("Professor", "Student")
    private val disposables = CompositeDisposable()
    override fun resId(): Int {
        return R.layout.fragment_signup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()
        setupSpinner()
    }

    private fun setupSpinner() {
        val adapter = object: ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item, roles) {
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

    private fun setupViewListeners() {
        binding?.btnLogin?.setOnClickListener {
            val selectedPosition = binding!!.spinner.selectedItemPosition
            val selectedItem = roles[selectedPosition]

            viewModel
                .signUp(
                    username = binding!!.etEmail.text.toString(),
                    password = binding!!.etPassword.text.toString(),
                    fName = binding!!.etFirstname.text.toString(),
                    lName = binding!!.etLastname.text.toString(),
                    role = selectedItem
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {

                    },
                    onComplete = {
                        Toast.makeText(requireContext(), "User saved!", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                )
                .addTo(disposables)
        }
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