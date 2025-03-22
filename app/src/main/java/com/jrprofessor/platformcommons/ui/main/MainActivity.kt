package com.jrprofessor.platformcommons.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.jrprofessor.platformcommons.R
import com.jrprofessor.platformcommons.dagger.DaggerBaseActivity
import com.jrprofessor.platformcommons.databinding.ActivityMainBinding
import com.jrprofessor.platformcommons.workmanager.SyncWorker
import java.time.Duration

class MainActivity : DaggerBaseActivity() {
    val binding by lazy {
        ActivityMainBinding.bind(
            LayoutInflater.from(this).inflate(R.layout.activity_main, null, true)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
        /** default fragment add*/
        addDefaultFragment()
        initListener()
        /** handle hardware back button or gesture*/
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (currentFragment is HomeFragment) {
                    showExitDialog()
                } else {
                    supportFragmentManager.popBackStack()
                }
            }
        })
        /** when fragment change than floating button hide*/
        supportFragmentManager.addOnBackStackChangedListener {
            toggleFabVisibility()
        }
    }

    /** work manager for one time request*/
    private fun setOneTimeWorkRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInitialDelay(Duration.ofSeconds(10))
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(10)
            ).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun toggleFabVisibility() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment is HomeFragment) {
            binding.floatingBtn.show() // Show FAB on HomeFragment
        } else {
            binding.floatingBtn.hide() // Hide FAB in other fragments
        }
    }

    private fun initListener() {
        binding.apply {
            floatingBtn.setOnClickListener {
                val fragment = UserAddFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .add(
                        R.id.fragmentContainer,
                        fragment,
                        UserAddFragment.TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun addDefaultFragment() {
        val fragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainer,
                fragment,
                HomeFragment.TAG
            ).commit()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
//        setOneTimeWorkRequest()
    }
}