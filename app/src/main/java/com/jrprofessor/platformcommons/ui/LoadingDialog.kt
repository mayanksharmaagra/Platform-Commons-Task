package com.jrprofessor.platformcommons.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.ColorInt

class LoadingDialog(
    context: Context,
    @ColorInt progressColor: Int = Color.WHITE
) {
    private val progressBar by lazy {
        ProgressBar(context).apply {
            indeterminateDrawable?.setColorFilterMultiply(progressColor)
        }
    }

    private val dialog by lazy {
        Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(progressBar)
        }
    }

    fun show() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
fun Drawable.setColorFilterMultiply(@ColorInt colorInt: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = PorterDuffColorFilter(colorInt, PorterDuff.Mode.MULTIPLY)
        // colorFilter = BlendModeColorFilter(colorInt, BlendMode.MULTIPLY)
    } else {
        @Suppress("Deprecation")
        setColorFilter(colorInt, PorterDuff.Mode.MULTIPLY)
    }
}