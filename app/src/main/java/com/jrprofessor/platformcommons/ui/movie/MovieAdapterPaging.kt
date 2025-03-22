package com.jrprofessor.platformcommons.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jrprofessor.platformcommons.databinding.ItemMovieListBinding
import com.jrprofessor.platformcommons.response.Movie
import com.jrprofessor.platformcommons.ui.formatDate

class MovieAdapterPaging(val onMovieClick: (String?) -> Unit) :
    PagingDataAdapter<Movie, MovieAdapterPaging.MovieViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.setData(it) }
    }

    inner class MovieViewHolder(private val binding: ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(movie: Movie) {
            binding.apply {
                Glide.with(root.context).load("http://image.tmdb.org/t/p/w185"+movie.posterPath).into(ivMovieImage)
                Log.e("TAG", "setData: "+"http://image.tmdb.org/t/p/w185"+movie.posterPath )
                tvMovieName.text = movie.title
                tvReleaseDate.text = movie.releaseDate?.formatDate()
                clParent.setOnClickListener {
                    onMovieClick(movie.id)
                }
            }
        }
    }

    object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }
}
