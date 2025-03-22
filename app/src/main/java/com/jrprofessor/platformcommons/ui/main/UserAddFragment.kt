package com.jrprofessor.platformcommons.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseFragment
import com.jrprofessor.platformcommons.databinding.FragmentAddUserBinding
import com.jrprofessor.platformcommons.network.Status
import com.jrprofessor.platformcommons.roomdb.UserEntity
import com.jrprofessor.platformcommons.ui.isNetworkActive
import com.jrprofessor.platformcommons.ui.shortToast
import com.jrprofessor.platformcommons.viewmodel.HomeViewModel

class UserAddFragment : DaggerBaseFragment() {

    companion object {
        val TAG = "UserAddFragment"
        fun newInstance(): UserAddFragment {
            return UserAddFragment()
        }
    }

    private val binding by lazy {
        FragmentAddUserBinding.inflate(
            layoutInflater, null, true
        )
    }
    val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[HomeViewModel::class.java]
    }

    override fun getLayoutResId(): Int = R.layout.fragment_add_user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun injectDependencies() {
        (requireActivity().application as ProjectApplication)
            .androidInjector()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.apply {
            btnSubmit.setOnClickListener {
                if (isValidate()) {
                    if (requireActivity().isNetworkActive()) {
                        /**when network available than user data save in server*/
                        val body =
                            hashMapOf(
                                "name" to etName.text.toString(),
                                "job" to etJob.text.toString()
                            )
                        viewModel.saveUser(body)
                    } else {
                        /**when network not available than user data save in local DB*/
                        showLoading()
                        val user =
                            UserEntity(name = etName.text.toString(), job = etJob.text.toString())
                        viewModel.saveLocalDB(user)
                        dismissLoading()
                        requireActivity().shortToast("Data save in Local DB")
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        binding.apply {
            val name = etName.text.toString()
            val job = etName.text.toString()
            if (name.isEmpty()) {
                requireActivity().shortToast("Name field is empty")
                return false
            }
            if (job.isEmpty()) {
                requireActivity().shortToast("Job field is empty")
                return false
            }
        }
        return true
    }

    private fun initObserver() {
        viewModel.getUserSaveObserver().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    dismissLoading()
                    if (response.data != null) {
                        requireActivity().shortToast("User save successfully")
                        requireActivity().onBackPressed()
                    }
                }

                Status.ERROR -> {
                    dismissLoading()
                    requireActivity().shortToast(response.message.toString())
                }
            }
        }
    }
}