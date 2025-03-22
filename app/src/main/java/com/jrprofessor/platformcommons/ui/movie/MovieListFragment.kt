package com.jrprofessor.platformcommons.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseFragment
import com.jrprofessor.platformcommons.databinding.FragmentCommonBinding
import com.jrprofessor.platformcommons.network.Status
import com.jrprofessor.platformcommons.ui.isNetworkActiveWithMessage
import com.jrprofessor.platformcommons.ui.shortToast
import com.jrprofessor.platformcommons.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class MovieListFragment : DaggerBaseFragment() {

    companion object {
        val TAG = "MovieListFragment"
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
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
    lateinit var movieAdapter: MovieAdapterPaging

    override fun getLayoutResId(): Int = R.layout.fragment_common
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
        binding.tvToolbar.text = requireContext().getString(R.string.movie_list)
        movieAdapter = MovieAdapterPaging { movieId ->
            val fragment = MovieDetailFragment.newInstance(movieId)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .add(
                    R.id.fragmentContainer,
                    fragment,
                    MovieDetailFragment.TAG
                ).addToBackStack(null).commit()
        }
        binding.rvUsers.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            if(requireActivity().isNetworkActiveWithMessage()){
                showLoading()
                viewModel.movieList.observe(viewLifecycleOwner) { pagingData ->
                    dismissLoading()
                    movieAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
    }

}