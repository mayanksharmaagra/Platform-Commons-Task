package com.jrprofessor.platformcommons.ui

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.jrprofessor.platformcommons.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Context.isNetworkActiveWithMessage(): Boolean {
    val isNetworkActive = isNetworkActive()
    if (!isNetworkActive) {
        shortToast(R.string.connection_error_message)
    }
    return isNetworkActive
}
fun Context.isNetworkActive(): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
fun Context.shortToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(@StringRes resId: Int) {
    shortToast(getString(resId))
}
fun View.gone(){
    visibility=View.GONE
}
fun View.visible(){
    visibility=View.GONE
}
fun String.formatDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)

    val date = LocalDate.parse(this, inputFormatter)
    return date.format(outputFormatter)
}