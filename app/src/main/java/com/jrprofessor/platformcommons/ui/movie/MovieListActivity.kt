package com.jrprofessor.platformcommons.ui.movie

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseActivity
import com.jrprofessor.platformcommons.databinding.ActivityMovieListBinding

class MovieListActivity : DaggerBaseActivity() {
    companion object{
        fun start(context: Context,) {
            context.startActivity(
                Intent(context, MovieListActivity::class.java)
            )
        }
    }
    val binding by lazy {
        ActivityMovieListBinding.bind(
            LayoutInflater.from(this).inflate(R.layout.activity_movie_list, null, true)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addDefaultFragment()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (currentFragment is MovieListFragment) {
                    finish()
                } else {
                    supportFragmentManager.popBackStack()
                }
            }
        })
    }

    private fun addDefaultFragment() {
        val fragment = MovieListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainer,
                fragment,
                MovieListFragment.TAG
            ).commit()
    }
}