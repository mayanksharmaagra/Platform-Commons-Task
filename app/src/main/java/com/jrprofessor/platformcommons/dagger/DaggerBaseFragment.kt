package com.jrprofessor.platformcommons.dagger

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.jrprofessor.platformcommons.ui.LoadingDialog
import javax.inject.Inject

abstract class DaggerBaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var loadingDialog: LoadingDialog? = null
    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }


    // Abstract method to force child fragments to implement dependency injection
    protected abstract fun injectDependencies()
    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(requireActivity())
        }
        loadingDialog?.show()
    }

    fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog?.dismiss()
    }
}

