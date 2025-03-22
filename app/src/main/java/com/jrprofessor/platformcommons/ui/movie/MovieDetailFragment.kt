package com.jrprofessor.platformcommons.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseFragment
import com.jrprofessor.platformcommons.databinding.FragmentCommonBinding
import com.jrprofessor.platformcommons.databinding.FragmentMovieDetailBinding
import com.jrprofessor.platformcommons.network.Status
import com.jrprofessor.platformcommons.response.MovieDetailsResponse
import com.jrprofessor.platformcommons.ui.formatDate
import com.jrprofessor.platformcommons.ui.isNetworkActiveWithMessage
import com.jrprofessor.platformcommons.ui.shortToast
import com.jrprofessor.platformcommons.viewmodel.HomeViewModel

class MovieDetailFragment : DaggerBaseFragment() {

    companion object {
        val TAG = "MovieDetailFragment"
        val MOVIE_ID = "movieId"
        fun newInstance(movieId: String?): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId?.toInt()!!)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val movieId by lazy {
        requireArguments().getInt(MOVIE_ID)
    }

    private val binding by lazy {
        FragmentMovieDetailBinding.inflate(
            layoutInflater, null, true
        )
    }
    val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[HomeViewModel::class.java]
    }

    override fun getLayoutResId(): Int = R.layout.fragment_movie_detail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
//fragment injection
    override fun injectDependencies() {
        (requireActivity().application as ProjectApplication)
            .androidInjector()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        movie detail call
        if (requireActivity().isNetworkActiveWithMessage()) viewModel.getMovieDetail(movieId)
        initObserver()
    }

    private fun initObserver() {
        viewModel.getMovieDetailObserver().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    dismissLoading()
                    showMovieDetail(response.data)
                }

                Status.ERROR -> {
                    dismissLoading()
                    requireActivity().shortToast(response.message.toString())
                }
            }
        }
    }

    private fun showMovieDetail(movie: MovieDetailsResponse?) {
        movie?.let {
            binding.apply {
                Glide.with(root.context).load("http://image.tmdb.org/t/p/w185" + movie.posterPath)
                    .into(ivMovieImage)
                Glide.with(root.context).load("http://image.tmdb.org/t/p/w185" + movie.posterPath)
                    .into(ivMovieBg)
                tvMovieName.text = movie.title
                tvReleaseDate.text = "(${movie.releaseDate?.formatDate()})"
                tvDesc.text = movie.overview

                val screenWidth = resources.displayMetrics.widthPixels-300

                val imageHeight = (screenWidth * 16) / 9  // Calculate 9:16 height
                cvParent.layoutParams.height = imageHeight
                cvParent.layoutParams.width = screenWidth
                cvParent.requestLayout()
            }
        }
    }

}