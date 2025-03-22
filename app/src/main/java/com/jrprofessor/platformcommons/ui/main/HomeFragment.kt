package com.jrprofessor.platformcommons.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseFragment
import com.jrprofessor.platformcommons.databinding.FragmentCommonBinding
import com.jrprofessor.platformcommons.network.Status
import com.jrprofessor.platformcommons.ui.movie.MovieListActivity
import com.jrprofessor.platformcommons.ui.isNetworkActiveWithMessage
import com.jrprofessor.platformcommons.ui.shortToast
import com.jrprofessor.platformcommons.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : DaggerBaseFragment() {

    companion object {
        val TAG = "HomeFragment"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private val binding by lazy {
        FragmentCommonBinding.inflate(
            layoutInflater, null, true
        )
    }
    val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[HomeViewModel::class.java]
    }
    lateinit var userAdapter: UserAdapterPaging

    override fun getLayoutResId(): Int = R.layout.fragment_common
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    /** enable inject in fragment*/
    override fun injectDependencies() {
        (requireActivity().application as ProjectApplication)
            .androidInjector()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbar.text = requireContext().getString(R.string.user_data)
        /** call user api*/
        if (requireActivity().isNetworkActiveWithMessage()) {
//            viewModel.getUsers(page = 1)
        }
        /** adapter set and item listener*/
        userAdapter = UserAdapterPaging { user ->
            MovieListActivity.start(requireContext())
        }
        binding.rvUsers.adapter = userAdapter
        initObserver()
    }

    private fun initObserver() {
        viewModel.getUserListObserver().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    dismissLoading()
                    /** update user data*/
                    if (response.data != null && response.data.user?.isNotEmpty() == true) {
//                        userAdapter.setUser(response.data.user)
                    }
                }

                Status.ERROR -> {
                    dismissLoading()
                    requireActivity().shortToast(response.message.toString())
                }
            }
        }
        viewModel.getAllUser().observe(viewLifecycleOwner) { users ->
            Log.e(TAG, "onViewCreated: " + Gson().toJson(users))
        }
        lifecycleScope.launch {
            if(requireActivity().isNetworkActiveWithMessage()) {
                viewModel.userList.observe(viewLifecycleOwner) { pagingData ->
                    userAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
    }
}